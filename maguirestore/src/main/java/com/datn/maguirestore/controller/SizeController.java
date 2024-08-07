package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.SizeDTO;
import com.datn.maguirestore.service.SizeService;
import com.datn.maguirestore.util.ResponseUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/** tuyenpv - Size. */
@RestController
@RequestMapping("/api/v1/sizes")
@RequiredArgsConstructor
public class SizeController {

    private static final String ENTITY_NAME = "size";
    private final Logger log = LoggerFactory.getLogger(SizeController.class);

    private final SizeService sizeService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("")
    public ResponseEntity<List<SizeDTO>> getAllSizes () {
        log.debug("REST request to get  Sizes");

        return ResponseEntity.ok(sizeService.findAll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/removed")
    public ResponseEntity<List<SizeDTO>> getAllSizesRemoved () {
        log.debug("REST request to get  Sizes");

        return ResponseEntity.ok(sizeService.findDelete());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<SizeDTO> getSize(@PathVariable Long id) {
        log.debug("REST request to get Size : {}", id);
        Optional<SizeDTO> sizeDTO = sizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sizeDTO);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("")
    public ResponseEntity<SizeDTO> createSize(@RequestBody SizeDTO sizeDTO)
            throws URISyntaxException {
        log.debug("REST request to save size : {}", sizeDTO);

        SizeDTO result = sizeService.save(sizeDTO);
        return ResponseEntity.created(new URI("/api/v1/size" + result.getId())).body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<SizeDTO> updateSize(
            @PathVariable(value = "id", required = false) final Long id
            , @RequestBody SizeDTO sizeDTO) {
        log.debug("REST request to update Size : {}, {}", id, sizeDTO);

        SizeDTO result = sizeService.update(sizeDTO);
        return ResponseEntity.ok().body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable Long id) {
        log.debug("REST request to delete Size : {}", id);
        sizeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
