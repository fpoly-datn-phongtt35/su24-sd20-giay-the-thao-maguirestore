package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.AddressDTO;
import com.datn.maguirestore.repository.AddressRepository;
import com.datn.maguirestore.service.AddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

  private static final String ENTITY_NAME = "address";
  private final Logger log = LoggerFactory.getLogger(AddressController.class);
  private final AddressService addressService;

  private final AddressRepository addressRepository;

  public AddressController(AddressService addressService, AddressRepository addressRepository) {
    this.addressService = addressService;
    this.addressRepository = addressRepository;
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping("")
  public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO)
      throws URISyntaxException {
    log.debug("REST request to save Address : {}", addressDTO);

    AddressDTO result = addressService.save(addressDTO);
    return ResponseEntity.created(new URI("/api/v1/address" + result.getId())).body(result);
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping("")
  public ResponseEntity<Page<AddressDTO>> getAllAddresses(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(required = false, defaultValue = "id") String sortBy,
      @RequestParam(required = false, defaultValue = "ASC") String sortDirection,
      @RequestParam(required = false, defaultValue = "") String keyword) {
    log.debug("REST request to get all Addresses");
    return ResponseEntity.ok(addressService.findAll(page, size, sortBy, sortDirection, keyword));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping("/{id}")
  public ResponseEntity<AddressDTO> updateAddress(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody AddressDTO addressDTO)
      throws URISyntaxException {
    log.debug("REST request to update Brand : {}, {}", id, addressDTO);

    addressDTO.setId(id);
    AddressDTO result = addressService.update(addressDTO);
    return ResponseEntity.ok().body(result);
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
    log.debug("REST request to delete Address : {}", id);
    addressService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
