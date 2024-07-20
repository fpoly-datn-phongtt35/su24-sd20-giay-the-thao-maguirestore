package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.ShoesFileUploadMappingDTO;
import com.datn.maguirestore.entity.ShoesFileUploadMapping;
import com.datn.maguirestore.repository.ShoesFileUploadMappingRepository;
import com.datn.maguirestore.service.mapper.ShoesDetailsMapper;
import com.datn.maguirestore.service.mapper.ShoesFileUploadMappingMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoesFileUploadMappingService {

    private final ShoesFileUploadMappingRepository shoesFileUploadMappingRepository;

    private final ShoesFileUploadMappingMapper shoesFileUploadMappingMapper;

    private final ShoesDetailsMapper shoesDetailsMapper;

    public ShoesFileUploadMappingDTO save(ShoesFileUploadMappingDTO dto) {
//        ShoesFileUploadMapping shoesFileUploadMapping = shoesFileUploadMappingMapper.toEntity(shoesFileUploadMappingDTO);
        ShoesFileUploadMapping shoesFileUploadMapping = new ShoesFileUploadMapping();
        shoesFileUploadMapping.setFileUpload(dto.getFileUpload());
        shoesFileUploadMapping.setShoesDetails(shoesDetailsMapper.toEntity(dto.getShoesDetails()));
        shoesFileUploadMapping.setStatus(dto.getStatus());
        shoesFileUploadMapping = shoesFileUploadMappingRepository.save(shoesFileUploadMapping);

        ShoesFileUploadMappingDTO shoesFileUploadMappingDTO = new ShoesFileUploadMappingDTO();
        shoesFileUploadMappingDTO.setFileUpload(shoesFileUploadMapping.getFileUpload());
        shoesFileUploadMappingDTO.setShoesDetails(shoesDetailsMapper.toShoesDetailsEntity(shoesFileUploadMapping.getShoesDetails()));
        shoesFileUploadMappingDTO.setStatus(shoesFileUploadMapping.getStatus());

        return shoesFileUploadMappingDTO;
    }

    public ShoesFileUploadMappingDTO update(ShoesFileUploadMappingDTO shoesFileUploadMappingDTO) {
        ShoesFileUploadMapping shoesFileUploadMapping = shoesFileUploadMappingMapper.toEntity(shoesFileUploadMappingDTO);
        shoesFileUploadMapping = shoesFileUploadMappingRepository.save(shoesFileUploadMapping);
        return shoesFileUploadMappingMapper.toDto(shoesFileUploadMapping);
    }

    public Optional<ShoesFileUploadMappingDTO> partialUpdate(ShoesFileUploadMappingDTO shoesFileUploadMappingDTO) {

        return shoesFileUploadMappingRepository
                .findById(shoesFileUploadMappingDTO.getId())
                .map(existingShoesFileUploadMapping -> {
                    shoesFileUploadMappingMapper.partialUpdate(existingShoesFileUploadMapping, shoesFileUploadMappingDTO);

                    return existingShoesFileUploadMapping;
                })
                .map(shoesFileUploadMappingRepository::save)
                .map(shoesFileUploadMappingMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ShoesFileUploadMappingDTO> findAll(Pageable pageable) {
        return shoesFileUploadMappingRepository.findAll(pageable).map(shoesFileUploadMappingMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ShoesFileUploadMappingDTO> findOne(Long id) {
        return shoesFileUploadMappingRepository.findById(id).map(shoesFileUploadMappingMapper::toDto);
    }

    public void delete(Long id) {
        shoesFileUploadMappingRepository.deleteById(id);
    }
}
