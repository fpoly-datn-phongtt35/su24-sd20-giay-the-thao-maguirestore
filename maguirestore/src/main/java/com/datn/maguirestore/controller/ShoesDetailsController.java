package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.ShoesDetailsDTO;
import com.datn.maguirestore.repository.ShoesDetailsRepository;
import com.datn.maguirestore.service.ShoesDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shoes-details")
@RequiredArgsConstructor
public class ShoesDetailsController {

    private static final String ENTITY_NAME = "shoes-details";
    private final Logger log = LoggerFactory.getLogger(ShoesDetailsController.class);
    private final ShoesDetailsService shoesDetailsService;

    private final ShoesDetailsRepository shoesDetailsRepository;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/shoes-details")
    public ResponseEntity<ShoesDetailsDTO> createShoesDetails(@RequestBody ShoesDetailsDTO shoesDetailsDTO)
            throws URISyntaxException {
        log.debug("REST request to save ShoesDetails : {}", shoesDetailsDTO);

        ShoesDetailsDTO result = shoesDetailsService.save(shoesDetailsDTO);
        return ResponseEntity.created(new URI("/api/shoes-details/" + result.getId())).body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/shoes-details/{id}")
    public ResponseEntity<ShoesDetailsDTO> updateShoesDetails(
            @PathVariable(value = "id", required = false) final Long id, @RequestBody ShoesDetailsDTO shoesDetailsDTO)
            throws URISyntaxException {
        log.debug("REST request to update ShoesDetails : {}, {}", id, shoesDetailsDTO);

        ShoesDetailsDTO result = shoesDetailsService.update(shoesDetailsDTO);
        return ResponseEntity.ok().body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/shoes-details")
    public ResponseEntity<List<ShoesDetailsDTO>> getAllShoesDetails() {
        log.debug("REST request to get a page of ShoesDetails");
        return ResponseEntity.ok(shoesDetailsService.fillAll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/shoes-details/{id}")
    public ResponseEntity<Void> deleteShoesDetails(@PathVariable Long id) {
        log.debug("REST request to delete ShoesDetails : {}", id);
        shoesDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
