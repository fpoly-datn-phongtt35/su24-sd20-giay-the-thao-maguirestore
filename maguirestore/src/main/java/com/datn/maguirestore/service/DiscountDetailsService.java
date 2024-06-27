package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.DiscountDetailsDTO;
import com.datn.maguirestore.entity.Discount;
import com.datn.maguirestore.entity.DiscountShoesDetails;
import com.datn.maguirestore.entity.Shoes;
import com.datn.maguirestore.payload.response.*;
import com.datn.maguirestore.repository.DiscountRepository;
import com.datn.maguirestore.repository.DiscountShoesDetailsRepository;
import com.datn.maguirestore.repository.ShoesRepository;
import com.datn.maguirestore.service.mapper.DiscountShoesDetailsMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiscountDetailsService {

    private final DiscountShoesDetailsRepository discountShoesDetailsRepository;

    private final DiscountRepository discountRepository;
    private final ShoesRepository shoesRepository;
    private final DiscountShoesDetailsMapper mapper;

    public DiscountDetaildResponseDTO save(DiscountDetailsDTO dto) throws Exception {
        DiscountShoesDetails discountShoesDetails = new DiscountShoesDetails();
        discountShoesDetails.setStatus(dto.getStatus());

        // Tìm discount từ ID
        Discount discount = discountRepository.findById(dto.getDiscount().getId())
                .orElseThrow(() -> new Exception("DiscountId not found"));

        // Tìm shoes details từ ID
        Shoes shoesDetails = shoesRepository.findById(dto.getId())
                .orElseThrow(() -> new Exception("Shoes detailsID not found"));

        discountShoesDetails.setDiscount(discount);
        discountShoesDetails.setShoesDetails(shoesDetails);
        discountShoesDetailsRepository.save(discountShoesDetails);

        DiscountDetaildResponseDTO detailsDTO = new DiscountDetaildResponseDTO();
        detailsDTO.setId(discountShoesDetails.getId());
        detailsDTO.setStatus(discountShoesDetails.getStatus());

        // Build DiscountDTO
        DiscountResponseDTO discountDTO = new DiscountResponseDTO();
        discountDTO.setId(discount.getId());
        discountDTO.setDiscountMethod(discount.getDiscountMethod());
        discountDTO.setDiscountAmount(discount.getDiscountAmount());
        discountDTO.setStartDate(discount.getStartDate());
        discountDTO.setEndDate(discount.getEndDate());

        // Build BrandDTO
        BrandResponseDTO brandDTO = new BrandResponseDTO();
        brandDTO.setId(shoesDetails.getBrand().getId());
        brandDTO.setBrandName(shoesDetails.getBrand().getName());

        // Build CategoryDTO
        CategoryResponseDTO categoryDTO = new CategoryResponseDTO();
        categoryDTO.setId(shoesDetails.getCategory().getId());
        categoryDTO.setCategoryName(shoesDetails.getCategory().getName());

        // Build ShoesDetailsDTO
        ShoesResponseDTO shoesDetailsDTO = new ShoesResponseDTO();
        shoesDetailsDTO.setId(shoesDetails.getId());
        shoesDetailsDTO.setName(shoesDetails.getName());
        shoesDetailsDTO.setBrand(brandDTO);
        shoesDetailsDTO.setCategory(categoryDTO);

        detailsDTO.setDiscount(discountDTO);
        detailsDTO.setShoesDetails(shoesDetailsDTO);

        return detailsDTO;
    }

    public List<DiscountDetailsDTO> findAll() {
        List<DiscountShoesDetails> discountDetailsList = discountShoesDetailsRepository.findAll();
        return discountDetailsList.stream()
            .map(mapper::convertDTO)
            .collect(Collectors.toList());
    }

}