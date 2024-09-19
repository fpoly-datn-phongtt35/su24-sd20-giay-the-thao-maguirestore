package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.CartDetailsDTO;
import com.datn.maguirestore.dto.CartDetailsRequestDTO;
import com.datn.maguirestore.entity.Cart;
import com.datn.maguirestore.entity.CartDetails;
import com.datn.maguirestore.repository.CartDetailsRepository;
import com.datn.maguirestore.repository.CartRepository;
import com.datn.maguirestore.repository.ShoesDetailsRepository;
import com.datn.maguirestore.service.mapper.CartDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CartDetailsService {

    private final Logger log = LoggerFactory.getLogger(CartDetailsService.class);

    private final CartDetailsRepository cartDetailsRepository;
    private final CartRepository cartRepository;
    private final ShoesDetailsRepository shoesDetailsRepository;

    private final CartDetailsMapper cartDetailsMapper;

    public CartDetailsDTO save(CartDetailsRequestDTO requestDTO) {
        log.debug("Request to save Cart : {}", requestDTO);
        CartDetails cart = dtoToEntity(requestDTO);
        cart.setStatus(1);
        cart= cartDetailsRepository.save(cart);
        return cartDetailsMapper.toDto(cart);
    }

    public CartDetailsDTO update(CartDetailsRequestDTO requestDTO) {
        log.debug("Request to update Cart : {}", requestDTO);
        CartDetails cart = dtoToEntity(requestDTO);
        cart.setStatus(1);
        cart= cartDetailsRepository.save(cart);
        return cartDetailsMapper.toDto(cart);
    }

    @Transactional(readOnly = true)
    public List<CartDetailsDTO> fillAll() {
        log.debug("Request to get all Cart");
        return cartDetailsRepository.findAll().stream()
                .map(cartDetailsMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public CartDetailsDTO findByID(Long id) {
        log.debug("Request to get a Cart");

        return cartDetailsRepository.findById(id)
                .map(cartDetailsMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with id " + id));
    }

    public void delete(Long id) {

        Optional<CartDetails> optionalCart = cartDetailsRepository.findById(id);
        if(optionalCart.isPresent()) {
            CartDetails cart = optionalCart.get();
//            cart.setStatus(0);
            cartDetailsRepository.delete(cart);
        }
    }

    public CartDetails dtoToEntity(CartDetailsRequestDTO requestDTO) {
        CartDetails details = new CartDetails();
        details.setId(requestDTO.getId());
        details.setStatus(requestDTO.getStatus());
        details.setQuantity(requestDTO.getQuantity());
        details.setCreatedBy(requestDTO.getCreatedBy());
        details.setCreatedDate(requestDTO.getCreatedDate());
        details.setLastModifiedBy(requestDTO.getLastModifiedBy());
        details.setLastModifiedDate(requestDTO.getLastModifiedDate());
        details.setCart(cartRepository.findById(requestDTO.getCart()).get());
        details.setShoesDetails(shoesDetailsRepository.findById(requestDTO.getShoesDetails()).get());
        return details;
    }

//    @Transactional(readOnly = true)
//    public Optional<CartDetailsDTO> findOne(Long id) {
//        log.debug("Request to get CartDetails : {}", id);
//        return cartDetailsRepository.findById(id).map(CartDetailsDTO::new);
//    }

    public Long countByCart(Cart cart) {
        return cartDetailsRepository.countByCart(cart);
    }
}
