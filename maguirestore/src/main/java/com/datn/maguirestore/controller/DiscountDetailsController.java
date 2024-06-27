package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.DiscountDetailsDTO;
import com.datn.maguirestore.dto.DiscountShoesDetailsDTO;
import com.datn.maguirestore.entity.DiscountShoesDetails;
import com.datn.maguirestore.payload.response.DiscountDetaildResponseDTO;
import com.datn.maguirestore.repository.DiscountShoesDetailsRepository;
import com.datn.maguirestore.service.DiscountDetailsService;
import com.datn.maguirestore.util.HeaderUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/discount-details")
@RequiredArgsConstructor
public class DiscountDetailsController {

    private static final String ENTITY_NAME = "discountShoesDetails";

    @Value("${clientApp.name}")
    private String applicationName;

    private final DiscountDetailsService service;

    @PostMapping("")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<DiscountDetaildResponseDTO> createDiscountShoesDetails(@RequestBody DiscountDetailsDTO discountShoesDetailsDTO)
            throws Exception {

        DiscountDetaildResponseDTO result = service.save(discountShoesDetailsDTO);
        return ResponseEntity
                .created(new URI("/api/discount-details/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @GetMapping("")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<DiscountDetailsDTO> getDiscountDetailsList() {
        return service.findAll();
    }

}
