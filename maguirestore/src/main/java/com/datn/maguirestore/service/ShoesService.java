package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.ShoesDTO;
import com.datn.maguirestore.entity.Shoes;
import com.datn.maguirestore.repository.ShoesRepository;
import com.datn.maguirestore.service.mapper.ShoesMapper;
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
public class ShoesService {
    private final Logger log = LoggerFactory.getLogger(ShoesService.class);

    private final ShoesRepository shoesRepository;

    private final ShoesMapper shoesMapper;


    public ShoesDTO save(ShoesDTO shoesDTO) {
        log.debug("Request to save Shoes : {}", shoesDTO);

        Shoes shoes = shoesMapper.toEntity(shoesDTO);
        shoes.setStatus(1);
        shoes = shoesRepository.save(shoes);
        return shoesMapper.toDto(shoes);
    }

    public ShoesDTO update(ShoesDTO shoesDTO) {
        log.debug("Request to update Shoes : {}", shoesDTO);

        Shoes shoes = shoesMapper.toEntity(shoesDTO);
        shoes.setStatus(1);
        shoes = shoesRepository.save(shoes);
        return shoesMapper.toDto(shoes);
    }


    public List<ShoesDTO> fillAll(){
        log.debug("Request to get all Shoes");

        return shoesRepository.findAll().stream()
                .map(shoesMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void delete(Long id){
        log.debug("Request to mark Shoes as inactive: {}", id);

        Optional<Shoes> shoesOptional = shoesRepository.findById(id);
        if (shoesOptional.isPresent()){
            Shoes shoes = shoesOptional.get();
            shoes.setStatus(0);
            shoesRepository.save(shoes);
        }
    }


}
