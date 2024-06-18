package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.CartDTO;
import com.datn.maguirestore.entity.Cart;
import com.datn.maguirestore.repository.CartRepository;
import com.datn.maguirestore.service.mapper.CartMapper;
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
public class CartService {

    private final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    public CartDTO save(CartDTO cartDTO) {
        log.debug("Request to save Cart : {}", cartDTO);
        Cart cart = cartMapper.toEntity(cartDTO);
        cart.setStatus(1);
        cart= cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartDTO update(CartDTO cartDTO) {
        log.debug("Request to update Cart : {}", cartDTO);
        Cart cart = cartMapper.toEntity(cartDTO);
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


}
