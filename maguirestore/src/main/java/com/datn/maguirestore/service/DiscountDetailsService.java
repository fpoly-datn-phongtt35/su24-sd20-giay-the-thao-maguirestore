package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.BrandDTO;
import com.datn.maguirestore.dto.CategoryDTO;
import com.datn.maguirestore.dto.DiscountDTO;
import com.datn.maguirestore.dto.DiscountDetailsDTO;
import com.datn.maguirestore.dto.ShoesDTO;
import com.datn.maguirestore.entity.Discount;
import com.datn.maguirestore.entity.DiscountShoesDetails;
import com.datn.maguirestore.entity.Shoes;
import com.datn.maguirestore.payload.request.DiscountDetailsRequest;
import com.datn.maguirestore.payload.response.*;
import com.datn.maguirestore.repository.DiscountRepository;
import com.datn.maguirestore.repository.DiscountShoesDetailsRepository;
import com.datn.maguirestore.repository.ShoesRepository;
import com.datn.maguirestore.service.mapper.DiscountShoesDetailsMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public DiscountDetailsDTO save(DiscountDetailsRequest dto) throws Exception {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        DiscountShoesDetails discountShoesDetails = new DiscountShoesDetails();
        discountShoesDetails.setStatus(1);
        discountShoesDetails.setCreatedBy(loggedInUser);

        Discount discount = discountRepository.findById(dto.getDiscountId())
                .orElseThrow(() -> new Exception("DiscountId not found"));

        Shoes shoes = shoesRepository.findById(dto.getShoeId())
                .orElseThrow(() -> new Exception("Shoes detailsID not found"));

        discountShoesDetails.setDiscount(discount);
        discountShoesDetails.setShoes(shoes);
        discountShoesDetailsRepository.save(discountShoesDetails);

        DiscountDetailsDTO detailsDTO = new DiscountDetailsDTO();
        detailsDTO.setId(discountShoesDetails.getId());
        detailsDTO.setStatus(discountShoesDetails.getStatus());

        // Build DiscountDTO
        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setId(discount.getId());
        discountDTO.setCode(discount.getCode());
        discountDTO.setName(discount.getName());
        discountDTO.setDiscountMethod(discount.getDiscountMethod());
        discountDTO.setDiscountAmount(discount.getDiscountAmount());
        discountDTO.setStartDate(discount.getStartDate());
        discountDTO.setEndDate(discount.getEndDate());

        // Build BrandDTO
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(shoes.getBrand().getId());
        brandDTO.setName(shoes.getBrand().getName());

        // Build CategoryDTO
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(shoes.getCategory().getId());
        categoryDTO.setName(shoes.getCategory().getName());

        // Build ShoesDetailsDTO
        ShoesDTO shoesResponseDTO = new ShoesDTO();
        shoesResponseDTO.setId(shoes.getId());
        shoesResponseDTO.setName(shoes.getName());
        shoesResponseDTO.setBrand(brandDTO);
        shoesResponseDTO.setCategory(categoryDTO);

        detailsDTO.setDiscount(discountDTO);
        detailsDTO.setShoes(shoesResponseDTO);

        return detailsDTO;
    }

    public Optional<DiscountDetailsDTO> findById(Long id) {
        return discountShoesDetailsRepository.findById(id).map(mapper::convertDTO);
    }

    public List<DiscountDetailsDTO> findAll() {
        List<DiscountShoesDetails> discountDetailsList = discountShoesDetailsRepository.findAll();
        return discountDetailsList.stream()
            .map(mapper::convertDTO)
            .collect(Collectors.toList());
    }

}
