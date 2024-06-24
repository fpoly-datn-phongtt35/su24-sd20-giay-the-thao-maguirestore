package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.DiscountDetailsDTO;
import com.datn.maguirestore.errors.BadRequestAlertException;
import com.datn.maguirestore.payload.response.DiscountDetaildResponseDTO;
import com.datn.maguirestore.repository.DiscountShoesDetailsRepository;
import com.datn.maguirestore.service.DiscountDetailsService;
import com.datn.maguirestore.util.HeaderUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/discount-details")
@RequiredArgsConstructor
public class DiscountDetailsController {

    private static final String ENTITY_NAME = "discountShoesDetails";

    @Value("${clientApp.name}")
    private String applicationName;

    private final DiscountDetailsService service;

    private final DiscountShoesDetailsRepository discountShoesDetailsRepository;

    @PostMapping("")
    @SecurityRequirement(name = "Bearer Authentication")
//    public ResponseEntity<DiscountDetailsDTO> createDiscountShoesDetails(@RequestBody DiscountDetailsDTO discountDetailsDTO)
//            throws Exception {
//
//        DiscountDetailsDTO result = service.save(discountDetailsDTO);
//        return ResponseEntity
//                .created(new URI("/api/discount-details" + result.getId()))
//                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//                .body(result);
//    }
    public ResponseEntity<DiscountDetaildResponseDTO> createDiscountShoesDetails(@RequestBody DiscountDetailsDTO discountShoesDetailsDTO)
            throws Exception {

        DiscountDetaildResponseDTO result = service.save(discountShoesDetailsDTO);
        return ResponseEntity
                .created(new URI("/api/discount-details/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


}
