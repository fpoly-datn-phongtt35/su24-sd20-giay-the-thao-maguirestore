package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.DiscountCreateDTO;
import com.datn.maguirestore.dto.DiscountDTO;
import com.datn.maguirestore.dto.DiscountResponseDTO;
import com.datn.maguirestore.errors.BadRequestAlertException;
import com.datn.maguirestore.repository.DiscountRepository;
import com.datn.maguirestore.service.DiscountService;
import com.datn.maguirestore.util.HeaderUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/discount")
@Slf4j
@RequiredArgsConstructor
public class DiscountController {

    private static final String ENTITY_NAME = "discount";

    @Value("${clientApp.name}")
    private String applicationName;

    private final DiscountService discountService;

    private final DiscountRepository discountRepository;

    /**
     * {@code POST  /discounts} : Create a new discount.
     * @param createDTO
     * @return
     * @throws URISyntaxException
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/")
    public ResponseEntity<DiscountDTO> createDiscount(@Valid @RequestBody DiscountCreateDTO createDTO) throws URISyntaxException {
        log.debug("REST request to save Discount : {}", createDTO);
        DiscountDTO discountDTO = discountService.createDiscount(createDTO);
        return ResponseEntity
                .created(new URI("/api/v1/discount/" + discountDTO.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, discountDTO.getId().toString()))
                .body(discountDTO);
    }

    /**
     * Updates an existing discount.
     * @param id
     * @param discountDTO
     * @return
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<DiscountDTO> updateDiscount(@PathVariable(value = "id", required = false) final Long id,
                                                      @RequestBody DiscountCreateDTO discountDTO) {
        if (null == discountDTO.getId()) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "null");
        }
        if (!Objects.equals(id, discountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "invalid");
        }
        if (!discountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "notfound");
        }
        DiscountDTO result = discountService.update(discountDTO, id);

        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, discountDTO.getId().toString()))
                .body(result);
    }

    /**
     * Get all discounts
     * @return List of all discounts
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/discounts")
    public List<DiscountDTO> getAllDiscounts() {
        log.debug("REST request to get all Discounts");
        return discountService.findAll();
    }

    /**
     * Get discount with id
     * @param id
     * @return discount
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<DiscountResponseDTO> getDiscountById(@PathVariable Long id) {
        log.debug("REST request to get Discount : {}", id);
        DiscountResponseDTO discountDTO = discountService.findById(id);
        return ResponseEntity.ok(discountDTO);
    }

    /**
     * Delete discount with id
     * @param id
     * @return ResponseEntity
     * @throws Exception
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) throws Exception {
        log.debug("REST request to delete Discount : {}", id);
        discountService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
