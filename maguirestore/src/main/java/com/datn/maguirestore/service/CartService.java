package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.CartDTO;
import com.datn.maguirestore.dto.CartRequestDTO;
import com.datn.maguirestore.dto.CartRequestPagination;
import com.datn.maguirestore.dto.CartResponseDTO;
import com.datn.maguirestore.entity.Cart;
import com.datn.maguirestore.entity.CartDetails;
import com.datn.maguirestore.repository.CartDetailsRepository;
import com.datn.maguirestore.repository.CartRepository;
import com.datn.maguirestore.repository.UserRepository;
import com.datn.maguirestore.service.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final CartMapper cartMapper;

    public CartDTO save(CartRequestDTO cartDTO) {
        log.debug("Request to save Cart : {}", cartDTO);
        Cart cart = DTOToEntity(cartDTO);
        cart.setStatus(1);
        cart= cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartDTO update(CartRequestDTO cartDTO) {
        log.debug("Request to update Cart : {}", cartDTO);
        Cart cart = DTOToEntity(cartDTO);
        cart.setStatus(1);
        cart= cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    @Transactional(readOnly = true)
    public List<CartDTO> fillAll() {
        log.debug("Request to get all Cart");

        return cartRepository.findAll().stream()
                .map(cartMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public CartResponseDTO fillDetailByCart(CartRequestPagination pagination) {
        Cart cart = cartRepository.findById(pagination.getId()).get();
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize());
        Page<CartDetails> list = cartDetailsRepository.getAllByCart(pagination.getId(), pageable);

        CartResponseDTO dto = new CartResponseDTO();
        dto.setCartDetailsList(list.getContent());
        dto.setCode(cart.getCode());
        dto.setCreatedBy(cart.getCreatedBy());
        dto.setCreatedDate(cart.getCreatedDate());
        dto.setStatus(cart.getStatus());
        dto.setId(cart.getId());
        dto.setUser(cart.getUser());
        dto.setLastModifiedBy(cart.getLastModifiedBy());
        dto.setLastModifiedDate(cart.getLastModifiedDate());
        return dto;
    }

    @Transactional(readOnly = true)
    public CartDTO findByID(Long id) {
        log.debug("Request to get a Cart");

        return cartRepository.findById(id)
                .map(cartMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with id " + id));
    }

    public void delete(Long id) {
        log.debug("Request to mark Cart as inactive: {}", id);

        Optional<Cart> optionalCart = cartRepository.findById(id);
        if(optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.setStatus(0);
            cartRepository.save(cart);
        }
    }


    public Cart DTOToEntity(CartRequestDTO cartRequestDTO) {
        Cart cart = new Cart();
        cart.setId(cartRequestDTO.getId());
        cart.setStatus(cartRequestDTO.getStatus());
        cart.setCode(cartRequestDTO.getCode());
        cart.setCreatedBy(cartRequestDTO.getCreatedBy());
        cart.setCreatedDate(cartRequestDTO.getCreatedDate());
        cart.setLastModifiedBy(cartRequestDTO.getLastModifiedBy());
        cart.setLastModifiedDate(cartRequestDTO.getLastModifiedDate());
        cart.setUser(userRepository.findById(cartRequestDTO.getUser()).get());
        return cart;
    }

    public List<Cart> findByOwnerIsCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("???????????????????????");
        System.out.println(authentication);
        System.out.println("???????????????????????");

//        if(authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return cartRepository.findByOwnerIsCurrentUser(userDetails.getUsername());
//        } else {
//            return cartRepository.findByOwnerIsCurrentUser("sonns");
//        }

    }
}
