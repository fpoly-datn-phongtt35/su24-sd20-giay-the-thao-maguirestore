package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.DiscountDetailsDTO;
import com.datn.maguirestore.errors.BadRequestAlertException;
import com.datn.maguirestore.payload.request.DiscountDetailsRequest;
import com.datn.maguirestore.payload.request.UpdateDiscountDetailsRequest;
import com.datn.maguirestore.payload.response.UpdateDiscountDetailsResponse;
import com.datn.maguirestore.repository.DiscountDetailsRepository;
import com.datn.maguirestore.service.DiscountDetailsService;
import com.datn.maguirestore.util.HeaderUtil;
import com.datn.maguirestore.util.ResponseUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/discount-details")
@RequiredArgsConstructor
@Slf4j
public class DiscountDetailsController {

    private static final String ENTITY_NAME = "discountDetails";

    @Value("${clientApp.name}")
    private String applicationName;

    private final DiscountDetailsService service;

    private final DiscountDetailsRepository discountDetailsRepository;

    @PostMapping("")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<DiscountDetailsDTO> createDiscountShoesDetails(@RequestBody DiscountDetailsRequest request)
            throws Exception {

        DiscountDetailsDTO result = service.save(request);
        return ResponseEntity
                .created(new URI("/api/discount-details/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UpdateDiscountDetailsResponse> updateDiscountShoesDetails(
        @PathVariable(value = "id") final Long id,
        @RequestBody UpdateDiscountDetailsRequest discountDetailsDTO
    ) {
        log.debug("REST request to update DiscountDetails : {}, {}", id, discountDetailsDTO);

        if (!discountDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UpdateDiscountDetailsResponse result = service.update(id, discountDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .body(result);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<DiscountDetailsDTO> getDiscountDetails(@PathVariable Long id) {
        log.debug("REST request to get DiscountSDetails : {}", id);
        Optional<DiscountDetailsDTO> discountShoesDetailsDTO = service.findById(id);
        return ResponseUtil.wrapOrNotFound(discountShoesDetailsDTO);
    }

    @GetMapping("")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<DiscountDetailsDTO> getDiscountDetailsList() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> deleteDiscountShoesDetails(@PathVariable Long id) throws Exception {
        log.debug("REST request to delete DiscountDetails : {}", id);
        service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

}
