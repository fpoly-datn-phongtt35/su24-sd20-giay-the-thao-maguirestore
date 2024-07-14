package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.ColorDTO;
import com.datn.maguirestore.service.ColorService;
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
@RequestMapping("/api/v1/color")
@RequiredArgsConstructor
public class ColorController {
    private static final String ENTITY_NAME = "color";
    private final Logger log = LoggerFactory.getLogger(ColorController.class);
    private final ColorService colorService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/")
    public ResponseEntity<ColorDTO> createColor(@RequestBody ColorDTO colorDTO) throws URISyntaxException {
        log.debug("REST request to save Color : {}", colorDTO);
        ColorDTO result = colorService.save(colorDTO);
        return ResponseEntity.created(new URI("/api/v1/color" + result.getId())).body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<ColorDTO> updateColor(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ColorDTO colorDTO) {
        log.debug("REST request to update Color : {}, {}", id, colorDTO);

        ColorDTO result = colorService.update(colorDTO);
        return ResponseEntity.ok().body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/")
    public ResponseEntity<List<ColorDTO>> getAllColor() {
        log.debug("REST request to get a page of Color");
        return ResponseEntity.ok(colorService.findAll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/removed")
    public ResponseEntity<List<ColorDTO>> getColorRemoved() {
        log.debug("REST request to get a page of Color");
        return ResponseEntity.ok(colorService.findRemoved());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<ColorDTO> getColorById(@PathVariable Long id) {
        log.debug("REST request to get Color by id");

        return ResponseEntity.ok(colorService.findbyId(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Long id) {
        log.debug("REST request to delete Color : {}", id);
        colorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
