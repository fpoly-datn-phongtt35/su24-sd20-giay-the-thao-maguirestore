package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.AddressDTO;
import com.datn.maguirestore.dto.CategoryDTO;
import com.datn.maguirestore.dto.SizeDTO;
import com.datn.maguirestore.repository.CategoryRepository;
import com.datn.maguirestore.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {

    private final Logger log = LoggerFactory.getLogger(CategoryController.class);
    private static final String ENTITY_NAME = "Category";

    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/category")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        log.debug("REST request to get all categories");
        return ResponseEntity.ok(categoryService.findAll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("")
    public ResponseEntity<CategoryDTO> createShoesCategory(@RequestBody @Valid CategoryDTO shoesCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save Category : {}", shoesCategoryDTO);

        CategoryDTO result = categoryService.save(shoesCategoryDTO);
        return ResponseEntity.created(new URI("/api/category" + result.getId())).body(result);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/sizes/{id}")
    public ResponseEntity<CategoryDTO> updateSize(
            @PathVariable(value = "id", required = false) final Long id
            , @RequestBody CategoryDTO categoryDTO)
            throws URISyntaxException {
        log.debug("REST request to update Size : {}, {}", id, categoryDTO);

        CategoryDTO result = categoryService.update(categoryDTO);
        return ResponseEntity.ok().body(result);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete category : {}", id);
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
