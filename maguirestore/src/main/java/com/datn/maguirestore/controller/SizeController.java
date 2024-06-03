package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.BrandDTO;
import com.datn.maguirestore.dto.SizeDTO;
import com.datn.maguirestore.repository.SizeRepository;
import com.datn.maguirestore.service.SizeService;
import com.datn.maguirestore.util.PaginationUtil;
import com.datn.maguirestore.util.ResponseUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/size")
@RequiredArgsConstructor
public class SizeController {

    private static final String ENTITY_NAME = "size";
    private final Logger log = LoggerFactory.getLogger(SizeController.class);

    private final SizeService sizeService;

    private final SizeRepository sizeRepository;

    @GetMapping("/sizes")
    public ResponseEntity<List<SizeDTO>> getAllSizes
            (@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Sizes");

        return ResponseEntity.ok(sizeService.findAll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/sizes")
    public ResponseEntity<SizeDTO> createSize(@RequestBody SizeDTO sizeDTO)
            throws URISyntaxException {
        log.debug("REST request to save size : {}", sizeDTO);

        SizeDTO result = sizeService.save(sizeDTO);
        return ResponseEntity.created(new URI("/api/size/" + result.getId())).body(result);
    }

    @PutMapping("/sizes/{id}")
    public ResponseEntity<SizeDTO> updateSize(
            @PathVariable(value = "id", required = false) final Long id
            , @RequestBody SizeDTO sizeDTO)
            throws URISyntaxException {
        log.debug("REST request to update Size : {}, {}", id, sizeDTO);

        SizeDTO result = sizeService.update(sizeDTO);
        return ResponseEntity.ok().body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/sizes/{id}")
    public ResponseEntity<SizeDTO> getSize(@PathVariable Long id) {
        log.debug("REST request to get Size : {}", id);
        Optional<SizeDTO> sizeDTO = sizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sizeDTO);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/sizes/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable Long id) {
        log.debug("REST request to delete Size : {}", id);
        sizeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
