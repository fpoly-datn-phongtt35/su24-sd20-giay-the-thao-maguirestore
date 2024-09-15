package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.ShoesDetailDTOCustom;
import com.datn.maguirestore.dto.ShoesDetailsDTO;
import com.datn.maguirestore.entity.Color;
import com.datn.maguirestore.entity.Shoes;
import com.datn.maguirestore.entity.ShoesDetails;
import com.datn.maguirestore.entity.Size;
import com.datn.maguirestore.payload.request.ShoesDetailCreateRequest;
import com.datn.maguirestore.repository.BrandRepository;
import com.datn.maguirestore.repository.ShoesDetailsRepository;
import com.datn.maguirestore.repository.ShoesRepository;
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

    private final BrandRepository brandRepository;
    private final ShoesRepository shoesRepository;


    public ShoesDetailsDTO save(ShoesDetailsDTO shoesDetailsDTO) {
        log.debug("Request to save ShoesDetails : {}", shoesDetailsDTO);

        ShoesDetails shoesDetails = shoesDetailsMapper.toEntity(shoesDetailsDTO);
        shoesDetails.setStatus(1);
        Shoes shoes = shoesRepository.findById(shoesDetailsDTO.getShoes().getId()).get();
        shoesDetails.setBrand(brandRepository.findById(shoes.getBrand().getId()).get());
        shoesDetails = shoesDetailsRepository.save(shoesDetails);
        return shoesDetailsMapper.toDto(shoesDetails);
    }

    public ShoesDetailsDTO save(ShoesDetailCreateRequest request) {
        log.debug("Request to save ShoesDetails : {}", request);

        ShoesDetails shoesDetails = new ShoesDetails();
        shoesDetails.setPrice(request.getPrice());
        shoesDetails.setImportPrice(request.getImportPrice());
        shoesDetails.setTax(request.getTax());
        shoesDetails.setQuantity(request.getQuantity());
        shoesDetails.setDescription(request.getDescription());

        shoesDetails.setStatus(1);
        shoesDetails.setCreatedBy(request.getCreatedBy());
        shoesDetails.setCreatedDate(request.getCreatedDate());

        Shoes shoes = new Shoes();
        shoes.setId(request.getShoesId());
        shoesDetails.setShoes(shoes);

        Size size = new Size();
        size.setId(request.getSizeId());
        shoesDetails.setSize(size);

        Color color = new Color();
        color.setId(request.getColorId());
        shoesDetails.setColor(color);

        shoesDetails = shoesDetailsRepository.save(shoesDetails);
        return shoesDetailsMapper.toDto(shoesDetails);
    }

    public ShoesDetailsDTO update(ShoesDetailsDTO shoesDetailsDTO) {
        log.debug("Request to update ShoesDetails : {}", shoesDetailsDTO);

//        ShoesDetails shoesDetails = shoesDetailsMapper.toEntity(shoesDetailsDTO);
        ShoesDetails shoesDetails = shoesDetailsRepository.findById(shoesDetailsDTO.getId()).get();
        shoesDetails.setDescription(shoesDetailsDTO.getDescription());
        shoesDetails.setImportPrice(shoesDetailsDTO.getImportPrice());
        shoesDetails.setPrice(shoesDetailsDTO.getPrice());
        shoesDetails.setQuantity(shoesDetailsDTO.getQuantity());
        shoesDetails.setTax(shoesDetailsDTO.getTax());
        shoesDetails.setCreatedBy(shoesDetailsDTO.getCreatedBy());
        shoesDetails.setCreatedDate(shoesDetailsDTO.getCreatedDate());
        shoesDetails.setLastModifiedBy(shoesDetailsDTO.getLastModifiedBy());
        shoesDetails.setLastModifiedDate(shoesDetailsDTO.getLastModifiedDate());
        shoesDetails.setBrand(shoesDetails.getShoes().getBrand());
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

    public ShoesDetailsDTO findById(Long aLong) {
        return shoesDetailsMapper.toDto(shoesDetailsRepository.findById(aLong).orElse(null));
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

    public List<ShoesDetailDTOCustom> getNewShoesDetail() {

        return shoesDetailsRepository.getNewShoesDetail();
    }

    public List<ShoesDetailDTOCustom> getNewDiscountShoesDetail() {
        return shoesDetailsRepository.getNewDiscountShoesDetail();
    }

    public List<ShoesDetailDTOCustom> getBestSellerShoesDetail() {

        return shoesDetailsRepository.getBestSeller();
    }
}
