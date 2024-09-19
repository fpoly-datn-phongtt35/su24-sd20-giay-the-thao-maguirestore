package com.datn.maguirestore.service;

import com.datn.maguirestore.config.Constants.STATUS;
import com.datn.maguirestore.dto.BrandDTO;
import com.datn.maguirestore.dto.CategoryDTO;
import com.datn.maguirestore.dto.DiscountDTO;
import com.datn.maguirestore.dto.DiscountDetailsDTO;
import com.datn.maguirestore.dto.ShoesDTO;
import com.datn.maguirestore.entity.Discount;
import com.datn.maguirestore.entity.DiscountDetails;
import com.datn.maguirestore.entity.Shoes;
import com.datn.maguirestore.errors.BadRequestAlertException;
import com.datn.maguirestore.payload.request.DiscountDetailsRequest;
import com.datn.maguirestore.payload.request.UpdateDiscountDetailsRequest;
import com.datn.maguirestore.payload.response.UpdateDiscountDetailsResponse;
import com.datn.maguirestore.repository.DiscountRepository;
import com.datn.maguirestore.repository.DiscountDetailsRepository;
import com.datn.maguirestore.repository.ShoesRepository;
import com.datn.maguirestore.service.dto2.DiscountShoesDetailsDTO;
import com.datn.maguirestore.service.mapper.DiscountShoesDetailsMapper;
import java.util.ArrayList;
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

    private final DiscountDetailsRepository discountDetailsRepository;

    private final DiscountRepository discountRepository;
    private final ShoesRepository shoesRepository;
    private final DiscountShoesDetailsMapper mapper;

    public DiscountDetailsDTO save(DiscountDetailsRequest dto) throws Exception {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        DiscountDetails discountDetails = new DiscountDetails();
        discountDetails.setStatus(1);
        discountDetails.setCreatedBy(loggedInUser);

        Discount discount = discountRepository.findById(dto.getDiscountId())
                .orElseThrow(() -> new Exception("DiscountId not found"));

        Shoes shoes = shoesRepository.findById(dto.getShoeId())
                .orElseThrow(() -> new Exception("Shoes detailsID not found"));

        discountDetails.setDiscount(discount);
        discountDetails.setShoes(shoes);
        discountDetailsRepository.save(discountDetails);

        DiscountDetailsDTO detailsDTO = new DiscountDetailsDTO();
        detailsDTO.setId(discountDetails.getId());
        detailsDTO.setStatus(discountDetails.getStatus());

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

    public UpdateDiscountDetailsResponse update(Long id, UpdateDiscountDetailsRequest request) {
        // Lấy Discount từ CSDL
        Discount discount = discountRepository.findById(request.getDiscountId())
            .orElseThrow(() -> new BadRequestAlertException("Discount not found", "discount", "notfound"));

        // Lấy Shoes từ CSDL
        Shoes shoes = shoesRepository.findById(request.getShoesId())
            .orElseThrow(() -> new BadRequestAlertException("Shoes not found", "shoes", "notfound"));

        // Lấy DiscountDetails hiện tại từ CSDL
        DiscountDetails discountDetails = discountDetailsRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("DiscountDetails not found", "discountDetails", "notfound"));

        // Cập nhật DiscountDetails
        discountDetails.setStatus(request.getStatus());
        discountDetails.setDiscount(discount);
        discountDetails.setShoes(shoes);

        discountDetailsRepository.save(discountDetails);

        UpdateDiscountDetailsResponse response = new UpdateDiscountDetailsResponse();
        response.setId(discountDetails.getId());
        response.setStatus(discountDetails.getStatus());
        response.setDiscountId(discount);
        response.setShoesId(shoes);

        return response;
    }

    public Optional<DiscountDetailsDTO> findById(Long id) {
        return discountDetailsRepository.findById(id).map(mapper::convertDTO);
    }

    public List<DiscountDetailsDTO> findAll() {
        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        return discountDetailsList.stream()
            .map(mapper::convertDTO)
            .collect(Collectors.toList());
    }

    public void delete(Long id) throws Exception {
        Optional<DiscountDetails> details = Optional.ofNullable(
            discountDetailsRepository.findById(id).orElseThrow(
                () -> new Exception("ID does not exists")));
        if (details.isPresent()) {
            DiscountDetails discountDetails = details.get();
            discountDetails.setStatus(STATUS.IN_ACTIVE);
            discountDetailsRepository.save(discountDetails);
        }
    }

}
