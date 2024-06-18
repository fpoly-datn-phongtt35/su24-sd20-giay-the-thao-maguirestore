package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.CartDetailsDTO;
import com.datn.maguirestore.repository.CartDetailsRepository;
import com.datn.maguirestore.service.CartDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart-details")
@RequiredArgsConstructor
public class CartDetailsController {

    private static final String ENTITY_NAME = "cart_details";
    private final Logger log = LoggerFactory.getLogger(CartDetailsController.class);
    private final CartDetailsService cartDetailsService;
    private final CartDetailsRepository cartDetailsRepository;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/cart-details/{id}")
    public ResponseEntity<CartDetailsDTO> getCartById(@PathVariable Long id) {
        log.debug("REST request to get CartDetail by id");

        return ResponseEntity.ok(cartDetailsService.findByID(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/cart-details")
    public ResponseEntity<CartDetailsDTO> createCart(@RequestBody CartDetailsDTO cartDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save CartDetail : {}", cartDetailsDTO);

        CartDetailsDTO result = cartDetailsService.save(cartDetailsDTO);
        return ResponseEntity.created(new URI("/api/cart-details/" + result.getId())).body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/cart-details/{id}")
    public ResponseEntity<CartDetailsDTO> updateCart(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody CartDetailsDTO cartDetailsDTO)
            throws URISyntaxException {
        log.debug("REST request to update Cart : {}, {}", id, cartDetailsDTO);

        CartDetailsDTO result = cartDetailsService.update(cartDetailsDTO);
        return ResponseEntity.ok().body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/cart-details")
    public ResponseEntity<List<CartDetailsDTO>> getAllCart() {
        log.debug("REST request to get a page of Cart");

        return ResponseEntity.ok(cartDetailsService.fillAll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/cart-details/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        log.debug("REST request to delete Cart : {}", id);
        cartDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
