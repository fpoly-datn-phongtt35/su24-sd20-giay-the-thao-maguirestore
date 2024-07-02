package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.ShoesDTO;
import com.datn.maguirestore.entity.Brand;
import com.datn.maguirestore.entity.Category;
import com.datn.maguirestore.entity.Shoes;
import com.datn.maguirestore.payload.request.ShoesCreateRequest;
import com.datn.maguirestore.repository.ShoesRepository;
import com.datn.maguirestore.service.mapper.ShoesMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
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

    public ShoesDTO save(ShoesCreateRequest request) {
        log.debug("Request to save Shoes : {}", request);

        Shoes shoes = new Shoes();
        shoes.setCode(request.getCode());
        shoes.setName(request.getName());
        shoes.setDescription(request.getDescription());
        shoes.setCreatedBy(request.getCreatedBy());
        shoes.setCreatedDate(request.getCreatedDate());
        shoes.setStatus(1);

        Brand brand = new Brand();
        brand.setId(request.getBrandId());
        shoes.setBrand(brand);

        Category category = new Category();
        category.setId(request.getCategoryId());
        shoes.setCategory(category);
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

        return shoesRepository.findAllActive().stream()
                .map(shoesMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<ShoesDTO> fillAllNoActive(){
        log.debug("Request to get all Shoes");

        return shoesRepository.findAllNoActive().stream()
                .map(shoesMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public ShoesDTO findById(Long aLong) {
        return shoesMapper.toDto(shoesRepository.findById(aLong).orElse(null));
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

    public List<Shoes> findByFiter(String key, Long categoryId) {
        return shoesRepository.findByFiter(key, categoryId);
    }

    public List<ShoesDTO> findByCategory(Long categoryId) {
        return shoesRepository.findByCategory(categoryId).stream()
                .map(shoesMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
