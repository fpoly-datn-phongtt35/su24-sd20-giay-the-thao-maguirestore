package com.datn.maguirestore.service;


import com.datn.maguirestore.dto.SizeDTO;
import com.datn.maguirestore.entity.Size;
import com.datn.maguirestore.repository.SizeRepository;
import com.datn.maguirestore.service.mapper.SizeMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** tuyenpv - Size. */
@Service
@Transactional
@RequiredArgsConstructor
public class SizeService {

    private final Logger log = LoggerFactory.getLogger(SizeService.class);

    private final SizeRepository sizeRepository;

    private final SizeMapper sizeMapper;

    public SizeDTO save(SizeDTO sizeDTO) {
        log.debug("Request to save Size : {}", sizeDTO);

        Size size = sizeMapper.toEntity(sizeDTO);
        size.setStatus(1);
        size = sizeRepository.save(size);

        return sizeMapper.toDto(size);
    }

    public SizeDTO update(SizeDTO sizeDTO) {
        log.debug("Request to update Size : {}", sizeDTO);

        sizeDTO.setStatus(1);

        Size size = sizeMapper.toEntity(sizeDTO);
        size = sizeRepository.save(size);
        return sizeMapper.toDto(size);
    }

    public Optional<SizeDTO> partialUpdate(SizeDTO sizeDTO) {
        log.debug("Request to partially update Size : {}", sizeDTO);

        return sizeRepository
                .findById(sizeDTO.getId())
                .map(existingSize -> {
                    sizeMapper.partialUpdate(existingSize, sizeDTO);

                    return existingSize;
                })
                .map(sizeRepository::save)
                .map(sizeMapper::toDto);
    }

    public List<SizeDTO> findAll() {
        log.debug("Request to get all Sizes with status = 1");

        List<SizeDTO> ds = sizeRepository.findAll().stream()
                .map(sizeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));

        return ds;
    }
    public List<SizeDTO> findDelete() {
        log.debug("Request to get all Sizes with status = 0");

        return sizeRepository.findByStatus(0).stream()
                .map(sizeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<SizeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sizes with status = 1");

        Page<Size> sizesWithStatus1 = sizeRepository.findByStatus(1, pageable);
        return sizesWithStatus1.map(sizeMapper::toDto);
    }

    public Optional<SizeDTO> findOne(Long id) {
        log.debug("Request to get Size : {}", id);
        return sizeRepository.findById(id).map(sizeMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Size : {}", id);

        sizeRepository
                .findById(id)
                .ifPresent(existingSize -> {
                    existingSize.setStatus(0);
                    sizeRepository.save(existingSize);
                });
    }

}
