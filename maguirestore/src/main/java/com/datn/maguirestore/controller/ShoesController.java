package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.ShoesDTO;
import com.datn.maguirestore.repository.ShoesRepository;
import com.datn.maguirestore.service.ShoesService;
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
@RequestMapping("/api/v1/shoes")
@RequiredArgsConstructor
public class ShoesController {

    private static final String ENTITY_NAME = "shoes";
    private final Logger log = LoggerFactory.getLogger(ShoesController.class);
    private final ShoesService shoesService;

    private final ShoesRepository shoesRepository;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/shoes")
    public ResponseEntity<ShoesDTO> createShoes(@RequestBody ShoesDTO shoesDTO)
            throws URISyntaxException {
        log.debug("REST request to save Shoes : {}", shoesDTO);

        ShoesDTO result = shoesService.save(shoesDTO);
        return ResponseEntity.created(new URI("/api/shoes/" + result.getId())).body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/shoes/{id}")
    public ResponseEntity<ShoesDTO> updateShoes(
            @PathVariable(value = "id", required = false) final Long id, @RequestBody ShoesDTO shoesDTO)
            throws URISyntaxException {
        log.debug("REST request to update Shoes : {}, {}", id, shoesDTO);

        ShoesDTO result = shoesService.update(shoesDTO);
        return ResponseEntity.ok().body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/shoes")
    public ResponseEntity<List<ShoesDTO>> getAllShoes() {
        log.debug("REST request to get a page of Shoes");
        return ResponseEntity.ok(shoesService.fillAll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/shoes/{id}")
    public ResponseEntity<Void> deleteShoes(@PathVariable Long id) {
        log.debug("REST request to delete Shoes : {}", id);
        shoesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}