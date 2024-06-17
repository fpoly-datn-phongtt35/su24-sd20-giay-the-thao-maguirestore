package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.ShoesDetailsDTO;
import com.datn.maguirestore.entity.ShoesDetails;
import com.datn.maguirestore.repository.ShoesDetailsRepository;
import com.datn.maguirestore.service.mapper.ShoesDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class ShoesDetailsService {
    private final Logger log = LoggerFactory.getLogger(ShoesDetailsService.class);

    private final ShoesDetailsRepository shoesDetailsRepository;

    private final ShoesDetailsMapper shoesDetailsMapper;


    public ShoesDetailsDTO save(ShoesDetailsDTO shoesDetailsDTO) {
        log.debug("Request to save ShoesDetails : {}", shoesDetailsDTO);

        ShoesDetails shoesDetails = shoesDetailsMapper.toEntity(shoesDetailsDTO);
        shoesDetails.setStatus(1);
        shoesDetails = shoesDetailsRepository.save(shoesDetails);
        return shoesDetailsMapper.toDto(shoesDetails);
    }

    public ShoesDetailsDTO update(ShoesDetailsDTO shoesDetailsDTO) {
        log.debug("Request to update ShoesDetails : {}", shoesDetailsDTO);

        ShoesDetails shoesDetails = shoesDetailsMapper.toEntity(shoesDetailsDTO);
        shoesDetails.setStatus(1);
        shoesDetails = shoesDetailsRepository.save(shoesDetails);
        return shoesDetailsMapper.toDto(shoesDetails);
    }


    public List<ShoesDetailsDTO> fillAll(){
        log.debug("Request to get all ShoesDetails");

        return shoesDetailsRepository.findAll().stream()
                .map(shoesDetailsMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void delete(Long id){
        log.debug("Request to mark ShoesDetail as inactive: {}", id);

        Optional<ShoesDetails> shoesDetailsOptional = shoesDetailsRepository.findById(id);
        if (shoesDetailsOptional.isPresent()){
            ShoesDetails shoesDetails = shoesDetailsOptional.get();
            shoesDetails.setStatus(0);
            shoesDetailsRepository.save(shoesDetails);
        }
    }


}
