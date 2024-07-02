package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.*;
import com.datn.maguirestore.repository.CartRepository;
import com.datn.maguirestore.service.CartService;
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
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private static final String ENTITY_NAME = "cart";
    private final Logger log = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long id) {
        log.debug("REST request to get Cart by id");

        return ResponseEntity.ok(cartService.findByID(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("")
    public ResponseEntity<CartDTO> createCart(@RequestBody CartRequestDTO cartDTO) throws URISyntaxException {
        log.debug("REST request to save Cart : {}", cartDTO);

        CartDTO result = cartService.save(cartDTO);
        return ResponseEntity.created(new URI("/api/v1/cart" + result.getId())).body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<CartDTO> updateCart(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody CartRequestDTO cartDTO)
            throws URISyntaxException {
        log.debug("REST request to update Cart : {}, {}", id, cartDTO);

        CartDTO result = cartService.update(cartDTO);
        return ResponseEntity.ok().body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("")
    public ResponseEntity<List<CartDTO>> getAllCart() {
        log.debug("REST request to get a page of Cart");

        return ResponseEntity.ok(cartService.fillAll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/detail")
    public ResponseEntity<CartResponseDTO> getDetailByCart(CartRequestPagination pagination) {
        log.debug("REST request to get a page of Cart");
        return ResponseEntity.ok(cartService.fillDetailByCart(pagination));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        log.debug("REST request to delete Cart : {}", id);
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
