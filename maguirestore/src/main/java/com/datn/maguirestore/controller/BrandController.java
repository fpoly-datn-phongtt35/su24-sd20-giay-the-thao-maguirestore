package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.BrandDTO;
import com.datn.maguirestore.dto.CategoryDTO;
import com.datn.maguirestore.repository.BrandRepository;
import com.datn.maguirestore.service.BrandService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor
public class BrandController {

    private static final String ENTITY_NAME = "brand";
    private final Logger log = LoggerFactory.getLogger(BrandController.class);
    private final BrandService brandService;

    private final BrandRepository brandRepository;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/brands")
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO)
            throws URISyntaxException {
        log.debug("REST request to save Brand : {}", brandDTO);

        BrandDTO result = brandService.save(brandDTO);
        return ResponseEntity.created(new URI("/api/brands/" + result.getId())).body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> updateBrand(
            @PathVariable(value = "id", required = false) final Long id, @RequestBody BrandDTO brandDTO)
            throws URISyntaxException {
        log.debug("REST request to update Brand : {}, {}", id, brandDTO);

        brandDTO.setId(id);
        BrandDTO result = brandService.update(brandDTO);
        return ResponseEntity.ok().body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("")
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        log.debug("REST request to get a page of Brands");
        return ResponseEntity.ok(brandService.findAll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/removed")
    public ResponseEntity<List<BrandDTO>> getBrandsRemoved() {
        log.debug("REST request to get a page of Color");
        return ResponseEntity.ok(brandService.findRemoved());
    }

    @GetMapping("/all")
    public ResponseEntity<List<BrandDTO>> getAllBrandx() {
        log.debug("REST request to get all brands");
        return ResponseEntity.ok(brandService.fillAlll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable Long id) {
        log.debug("REST request to get brand by id");

        return ResponseEntity.ok(brandService.findbyId(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        log.debug("REST request to delete Brand : {}", id);
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
