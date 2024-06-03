package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.BrandDTO;
import com.datn.maguirestore.entity.Brand;
import com.datn.maguirestore.repository.BrandRepository;
import com.datn.maguirestore.service.mapper.BrandMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService {

  private final Logger log = LoggerFactory.getLogger(BrandService.class);

  private final BrandRepository brandRepository;

  private final BrandMapper brandMapper;

  public BrandDTO save(BrandDTO brandDTO) {
    log.debug("Request to save Brand : {}", brandDTO);

    Brand brand = brandMapper.toEntity(brandDTO);
    brand.setStatus(1);
    brand = brandRepository.save(brand);
    return brandMapper.toDto(brand);
  }

  public BrandDTO update(BrandDTO brandDTO) {
    log.debug("Request to update Brand : {}", brandDTO);

    Brand brand = brandMapper.toEntity(brandDTO);

    brand.setStatus(1);

    brand = brandRepository.save(brand);

    return brandMapper.toDto(brand);
  }

  @Transactional(readOnly = true)
  public List<BrandDTO> findAll() {
    log.debug("Request to get all Brands");

    return brandRepository.findAll().stream()
        .map(brandMapper::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
  }

  public void delete(Long id) {
    log.debug("Request to mark Brand as inactive: {}", id);

    Optional<Brand> brandOptional = brandRepository.findById(id);
    if (brandOptional.isPresent()) {
      Brand brand = brandOptional.get();
      brand.setStatus(0); // Cập nhật trường status thành 0
      brandRepository.save(brand);
    }
  }
}