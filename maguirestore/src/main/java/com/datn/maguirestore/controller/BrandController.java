package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.BrandDTO;
import com.datn.maguirestore.service.BrandService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor
public class BrandController {

  private static final String ENTITY_NAME = "brand";
  private final Logger log = LoggerFactory.getLogger(BrandController.class);
  private final BrandService brandService;


  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping("")
  public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO)
      throws URISyntaxException {
    log.debug("REST request to save Brand : {}", brandDTO);

    BrandDTO result = brandService.save(brandDTO);
    return ResponseEntity.created(new URI("/api/v1/brand" + result.getId())).body(result);
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping("/{id}")
  public ResponseEntity<BrandDTO> updateBrand(
      @PathVariable(value = "id", required = false) final Long id, @RequestBody BrandDTO brandDTO) {
    log.debug("REST request to update Brand : {}, {}", id, brandDTO);

    brandDTO.setId(id);
    BrandDTO result = brandService.update(brandDTO);
    return ResponseEntity.ok().body(result);
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping("")
  public ResponseEntity<Page<BrandDTO>> getAllBrands(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false, defaultValue = "id") String sortBy,
      @RequestParam(required = false, defaultValue = "ASC") String sortDirection,
      @RequestParam(required = false, defaultValue = "") String keyword) {
    log.debug("REST request to get a page of Brands");
    return ResponseEntity.ok(brandService.findAll(page, size, sortBy, sortDirection, keyword));
  }
  @GetMapping("/all")
  public ResponseEntity<List<BrandDTO>> getAllBrandsx() {
    log.debug("REST request to get a page of Brands");
    return ResponseEntity.ok(brandService.findAll());
  }


  @SecurityRequirement(name = "Bearer Authentication")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
    log.debug("REST request to delete Brand : {}", id);
    brandService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
