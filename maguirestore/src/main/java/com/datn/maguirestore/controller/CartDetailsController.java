package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.*;
import com.datn.maguirestore.entity.Cart;
import com.datn.maguirestore.entity.User;
import com.datn.maguirestore.repository.CartDetailsRepository;
import com.datn.maguirestore.repository.UserRepository;
import com.datn.maguirestore.service.CartDetailsService;
import com.datn.maguirestore.service.CartService;
import com.datn.maguirestore.service.UserService;
import com.datn.maguirestore.util.HeaderUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cart-details")
@RequiredArgsConstructor
public class CartDetailsController {

    private static final String ENTITY_NAME = "cart_details";
    private final Logger log = LoggerFactory.getLogger(CartDetailsController.class);
    private final CartDetailsService cartDetailsService;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final CartDetailsRepository cartDetailsRepository;

    @Value("${clientApp.name}")
    private String applicationName;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<CartDetailsDTO> getCartById(@PathVariable Long id) {
        log.debug("REST request to get CartDetail by id");

        return ResponseEntity.ok(cartDetailsService.findByID(id));
    }

    @PutMapping("/update-quantity/{id}/{quantity}")
    public ResponseEntity<CartDetailsDTO> updateQuantityCartDetails(
            @PathVariable(value = "id", required = false)  Long id,
            @PathVariable(value = "quantity", required = false)  Long quantity
    ) {
//        cartDetailsService.findOne(id);
        CartDetailsDTO cartDetailsDTO = cartDetailsService.findByID(id);
        cartDetailsDTO.setQuantity((int) (cartDetailsDTO.getQuantity() + quantity));
        CartDetailsRequestDTO requestDTO = new CartDetailsRequestDTO();
        requestDTO.setId(cartDetailsDTO.getId());
        requestDTO.setCart(cartDetailsDTO.getCart().getId());
        requestDTO.setQuantity(cartDetailsDTO.getQuantity());
        requestDTO.setStatus(cartDetailsDTO.getStatus());
        requestDTO.setShoesDetails(cartDetailsDTO.getShoesDetails().getId());

        CartDetailsDTO result = cartDetailsService.update(requestDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cartDetailsDTO.getId().toString()))
                .body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("")
    public ResponseEntity<CartDetailsDTO> createCart(@RequestBody CartDetailsRequestDTO cartDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save CartDetail : {}", cartDetailsDTO);

        List<Cart> carts = cartService.findByOwnerIsCurrentUser();

        if(carts.size() == 0) {
            CartRequestDTO dto = new CartRequestDTO();
            dto.setStatus(1);
            User user = userRepository.findByLogin(cartDetailsDTO.getLastModifiedBy()).get();
            dto.setUser(user.getId());
            CartDTO cart = cartService.save(dto);
            cartDetailsDTO.setCart(cart.getId());
        } else {
            Cart cart1 = carts.get(0);
            cartDetailsDTO.setCart(cart1.getId());
        }

        CartDetailsDTO result = cartDetailsService.save(cartDetailsDTO);
        return ResponseEntity.created(new URI("/api/v1/cart-details/" + result.getId())).body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<CartDetailsDTO> updateCart(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody CartDetailsRequestDTO cartDetailsDTO) {
        log.debug("REST request to update Cart : {}, {}", id, cartDetailsDTO);

        CartDetailsDTO result = cartDetailsService.update(cartDetailsDTO);
        return ResponseEntity.ok().body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("")
    public ResponseEntity<List<CartDetailsDTO>> getAllCart() {
        log.debug("REST request to get a page of Cart");

        return ResponseEntity.ok(cartDetailsService.fillAll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/allCartDetail")
    public ResponseEntity<List<CartDetailDTO>> getAllPathCartDetail() {
        List<Cart> carts = cartService.findByOwnerIsCurrentUser();
        Cart cart1 = carts.get(0);
        List<CartDetailDTO> cartDetailsDTOS = cartDetailsRepository.findCartDetailsByCart_Id(cart1.getId());
        return ResponseEntity.ok().body(cartDetailsDTOS);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        List<Cart> cart = cartService.findByOwnerIsCurrentUser();
        Cart cart1 = cart.get(0);
        Long count = cartDetailsService.countByCart(cart1);
        return ResponseEntity.ok().body(count);
    }

    @PutMapping("/add-quantity/{soluong}/{id}")
    public ResponseEntity<CartDetailsDTO> addQuantityCartDetail(
            @PathVariable(value = "soluong", required = false) final Long soluong,
            @PathVariable(value = "id", required = false) final Long id
    ) {
        CartDetailsDTO cartDetailsDTO = cartDetailsService.findByID(id);

        CartDetailsRequestDTO requestDTO = new CartDetailsRequestDTO();
        requestDTO.setId(cartDetailsDTO.getId());
        requestDTO.setCart(cartDetailsDTO.getCart().getId());
        requestDTO.setQuantity(soluong.intValue());
        requestDTO.setStatus(cartDetailsDTO.getStatus());
        requestDTO.setShoesDetails(cartDetailsDTO.getShoesDetails().getId());
        CartDetailsDTO result = cartDetailsService.update(requestDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cartDetailsDTO.getId().toString()))
                .body(result);
    }

    @PutMapping("/reduce-quantity/{id}")
    public ResponseEntity<CartDetailsDTO> reduceQuantityCartDetails(@PathVariable(value = "id", required = false) final Long id) {
        CartDetailsDTO cartDetailsDTO = cartDetailsService.findByID(id);
        cartDetailsDTO.setQuantity(cartDetailsDTO.getQuantity() - 1);
        CartDetailsRequestDTO requestDTO = new CartDetailsRequestDTO();
        requestDTO.setId(cartDetailsDTO.getId());
        requestDTO.setCart(cartDetailsDTO.getCart().getId());
        requestDTO.setQuantity(cartDetailsDTO.getQuantity());
        requestDTO.setStatus(cartDetailsDTO.getStatus());
        requestDTO.setShoesDetails(cartDetailsDTO.getShoesDetails().getId());
        CartDetailsDTO result = cartDetailsService.update(requestDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cartDetailsDTO.getId().toString()))
                .body(result);
    }
}
