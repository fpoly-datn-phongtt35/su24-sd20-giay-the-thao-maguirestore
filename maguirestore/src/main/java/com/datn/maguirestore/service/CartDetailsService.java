package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.CartDTO;
import com.datn.maguirestore.dto.CartDetailsDTO;
import com.datn.maguirestore.entity.Cart;
import com.datn.maguirestore.entity.CartDetails;
import com.datn.maguirestore.repository.CartDetailsRepository;
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

    private final CartDetailsMapper cartDetailsMapper;

    public CartDetailsDTO save(CartDetailsDTO cartDetailsDTO) {
        log.debug("Request to save Cart : {}", cartDetailsDTO);
        CartDetails cart = cartDetailsMapper.toEntity(cartDetailsDTO);
        cart.setStatus(1);
        cart= cartDetailsRepository.save(cart);
        return cartDetailsMapper.toDto(cart);
    }

    public CartDetailsDTO update(CartDetailsDTO cartDetailsDTO) {
        log.debug("Request to update Cart : {}", cartDetailsDTO);
        CartDetails cart = cartDetailsMapper.toEntity(cartDetailsDTO);
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
        log.debug("Request to mark Cart as inactive: {}", id);

        Optional<CartDetails> optionalCart = cartDetailsRepository.findById(id);
        if(optionalCart.isPresent()) {
            CartDetails cart = optionalCart.get();
            cart.setStatus(0);
            cartDetailsRepository.save(cart);
        }
    }
}
