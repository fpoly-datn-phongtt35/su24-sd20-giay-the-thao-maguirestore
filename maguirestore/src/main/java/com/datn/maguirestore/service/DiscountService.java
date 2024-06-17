package com.datn.maguirestore.service;

import com.datn.maguirestore.config.Constants;
import com.datn.maguirestore.dto.*;
import com.datn.maguirestore.entity.Brand;
import com.datn.maguirestore.entity.Discount;
import com.datn.maguirestore.entity.DiscountShoesDetails;
import com.datn.maguirestore.errors.BadRequestAlertException;
import com.datn.maguirestore.payload.request.DiscountCreateRequest;
import com.datn.maguirestore.payload.request.DiscountUpdateRequest;
import com.datn.maguirestore.payload.response.DiscountCreateResponse;
import com.datn.maguirestore.payload.response.DiscountUpdateResponse;
import com.datn.maguirestore.repository.BrandRepository;
import com.datn.maguirestore.repository.DiscountRepository;
import com.datn.maguirestore.repository.DiscountShoesDetailsRepository;
import com.datn.maguirestore.repository.ShoesRepository;
import com.datn.maguirestore.service.mapper.DiscountMapper;
import com.datn.maguirestore.service.mapper.DiscountShoesDetailsMapper;
import com.datn.maguirestore.service.mapper.ShoesMapper;
import com.datn.maguirestore.util.DataUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;

    private final DiscountShoesDetailsRepository discountShoesDetailsRepository;

    private static final String ENTITY_NAME = "discount";

    public DiscountCreateResponse createDiscount(DiscountCreateRequest discountDTO) throws Exception {
        log.debug("Request to save Discount : {}", discountDTO);

        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Discount discount = new Discount();
        discount.setId(discountDTO.getId());
        discount.setCode(discountDTO.getCode());
        discount.setName(discountDTO.getName());
        discount.setStatus(Constants.STATUS.ACTIVE);

        switch (discountDTO.getDiscountMethod()) {
            case 1:
                discount.setDiscountMethod(Constants.DISCOUNT_METHOD.TOTAL_MONEY);
                break;
            case 2:
                discount.setDiscountMethod(Constants.DISCOUNT_METHOD.TOTAL_PERCENT);
                break;
            case 3:
                discount.setDiscountMethod(Constants.DISCOUNT_METHOD.PER_MONEY);
                break;
            case 4:
                discount.setDiscountMethod(Constants.DISCOUNT_METHOD.PER_PERCENT);
                break;
            default:;
        }

        discount.setCreatedBy(loggedUser);
        discount.setCreatedDate(Instant.now());
        discountRepository.save(discount);

        DiscountCreateResponse dto = new DiscountCreateResponse();
        dto.setId(discount.getId());
        dto.setCode(discount.getCode());
        dto.setName(discount.getName());
        dto.setDiscountMethod(discount.getDiscountMethod());
        dto.setStatus(discount.getStatus());
        dto.setCreatedBy(discount.getCreatedBy());
        dto.setCreatedDate(discount.getCreatedDate());

        return dto;
    }

    public DiscountUpdateResponse update(DiscountUpdateRequest discountDTO) {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Request to update Discount : {}", discountDTO);

        Discount discount = discountMapper.toDiscountEntity(discountDTO);
        discount.setStatus(discountDTO.getStatus());
        discount.setLastModifiedBy(loggedUser);
        discountRepository.save(discount);

        DiscountUpdateResponse dto = new DiscountUpdateResponse();
        dto.setId(discount.getId());
        dto.setCode(discount.getCode());
        dto.setName(discount.getName());
        dto.setStatus(discount.getStatus());
        dto.setDiscountMethod(discount.getDiscountMethod());
        dto.setLastModifiedBy(discount.getLastModifiedBy());
        dto.setLastModifiedDate(discount.getLastModifiedDate());

        return dto;
    }

    @Transactional(readOnly = true)
    public List<Discount> findAll() {
        log.debug("Request to get all Discounts");
        return discountRepository
                .findAllByStatus(Constants.STATUS.ACTIVE);
    }

    @Transactional(readOnly = true)
    public DiscountResponseDTO findById(Long id) {
        log.debug("Request to get Discount : {}", id);
        Discount discount = discountRepository.findByIdAndStatus(id, Constants.STATUS.ACTIVE);
        if (DataUtils.isNull(discount)) {
            throw new BadRequestAlertException("Chương trình khuyến mại không tồn tại", "discount", "exist");
        }
        DiscountResponseDTO discountResponse= discountMapper.toDiscountDTO(discount);

        return discountResponse;
    }

    public List<DiscountSearchDTO> search(String searchText) {
        return discountRepository.search(searchText);
    }

    public void delete(Long id) throws Exception {
        log.debug("Request to delete Discount : {}", id);
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Discount discount = discountRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Khong tim thay"));
        discount.setStatus(Constants.STATUS.DELETE);
        discount.setLastModifiedBy(loggedUser);
        discount.setLastModifiedDate(Instant.now().plus(7, ChronoUnit.HOURS));
        this.discountShoesDetailsRepository.updateStatus(Collections.singletonList(id), -1);
        discountRepository.save(discount);
    }

}
