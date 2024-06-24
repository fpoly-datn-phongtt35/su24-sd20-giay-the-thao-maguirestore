package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.AddressDTO;
import com.datn.maguirestore.entity.Address;
import com.datn.maguirestore.repository.AddressRepository;
import com.datn.maguirestore.service.mapper.AddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressService {

  private final Logger log = LoggerFactory.getLogger(AddressService.class);

  private final AddressRepository addressRepository;

  private final AddressMapper addressMapper;

  public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
    this.addressRepository = addressRepository;
    this.addressMapper = addressMapper;
  }

  public AddressDTO save(AddressDTO addressDTO) {
    log.debug("Request to save Address : {}", addressDTO);
    Address address = addressMapper.toEntity(addressDTO);
    address = addressRepository.save(address);
    return addressMapper.toDto(address);
  }

  public AddressDTO update(AddressDTO addressDTO) {
    log.debug("Request to update Address : {}", addressDTO);
    Address address = addressMapper.toEntity(addressDTO);
    address = addressRepository.save(address);
    return addressMapper.toDto(address);
  }

  @Transactional(readOnly = true)
  public Page<AddressDTO> findAll(
      int pageNo, int pageSize, String sortBy, String sortDirection, String keyword) {
    log.debug("Request to get all Addresses");
    Pageable pageable =
        PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

    return addressRepository.findAll(keyword, pageable).map(addressMapper::toDto);
  }

  public void delete(Long id) {
    log.debug("Request to delete Address : {}", id);
    addressRepository.deleteById(id);
  }
}
