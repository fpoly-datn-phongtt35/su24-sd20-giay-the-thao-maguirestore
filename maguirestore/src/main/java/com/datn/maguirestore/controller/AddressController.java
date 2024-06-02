package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.AddressDTO;
import com.datn.maguirestore.repository.AddressRepository;
import com.datn.maguirestore.service.AddressService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  @PostMapping("/addresses")
  public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO)
      throws URISyntaxException {
    log.debug("REST request to save Address : {}", addressDTO);

    AddressDTO result = addressService.save(addressDTO);
    return ResponseEntity.created(new URI("/api/addresses/" + result.getId())).body(result);
  }

  @GetMapping("/addresses")
  public ResponseEntity<List<AddressDTO>> getAllAddresses() {
    log.debug("REST request to get all Addresses");
    return ResponseEntity.ok(addressService.findAll());
  }

  @DeleteMapping("/addresses/{id}")
  public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
    log.debug("REST request to delete Address : {}", id);
    addressService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
