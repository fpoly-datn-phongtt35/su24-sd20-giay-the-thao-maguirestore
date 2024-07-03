package com.datn.maguirestore.service;

import com.datn.maguirestore.config.Constants;
import com.datn.maguirestore.dto.*;
import com.datn.maguirestore.entity.Discount;
import com.datn.maguirestore.errors.BadRequestAlertException;
import com.datn.maguirestore.payload.request.DiscountCreateRequest;
import com.datn.maguirestore.payload.request.DiscountUpdateRequest;
import com.datn.maguirestore.payload.response.DiscountResponse;
import com.datn.maguirestore.repository.DiscountRepository;
import com.datn.maguirestore.repository.DiscountShoesDetailsRepository;
import com.datn.maguirestore.service.mapper.DiscountMapper;
import com.datn.maguirestore.util.DataUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DiscountService {

  private final DiscountRepository discountRepository;
  private final DiscountMapper discountMapper;

  private final DiscountShoesDetailsRepository discountShoesDetailsRepository;

  private static final String ENTITY_NAME = "discount";

  public DiscountDTO createDiscount(DiscountCreateRequest discountDTO) {
    String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();

    Discount discount = new Discount();
    discount.setCode(discountDTO.getCode());
    discount.setName(discountDTO.getName());
    discount.setStatus(Constants.STATUS.ACTIVE);
    discount.setStartDate(discountDTO.getStartDate());
    discount.setEndDate(discountDTO.getEndDate());
    discount.setDiscountAmount(discountDTO.getDiscountAmount());

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
      default:
    }

    discount.setCreatedBy(loggedUser);
    discount.setCreatedDate(Instant.now());
    discountRepository.save(discount);

    DiscountDTO dto = new DiscountDTO();
    dto.setId(discount.getId());
    dto.setCode(discount.getCode());
    dto.setName(discount.getName());
    dto.setDiscountMethod(discount.getDiscountMethod());
    dto.setDiscountAmount(discount.getDiscountAmount());
    dto.setStartDate(discount.getStartDate());
    dto.setEndDate(discount.getEndDate());

    return dto;
  }

  public DiscountResponse update(Long id, DiscountUpdateRequest discountDTO) {
    String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
    log.debug("Request to update Discount : {}", discountDTO);

    Discount discount = discountRepository.findById(id)
        .orElseThrow(
            () -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "notfound"));

    discount.setCode(discountDTO.getCode());
    discount.setName(discountDTO.getName());
    discount.setStatus(discountDTO.getStatus());
    discount.setDiscountMethod(discountDTO.getDiscountMethod());
    discount.setStartDate(discountDTO.getStartDate());
    discount.setEndDate(discountDTO.getEndDate());
    discount.setDiscountAmount(discountDTO.getDiscountAmount());
    discount.setLastModifiedBy(loggedUser);
    discountRepository.save(discount);

    DiscountResponse dto = new DiscountResponse();
    dto.setId(discount.getId());
    dto.setCode(discount.getCode());
    dto.setName(discount.getName());
    dto.setStatus(discount.getStatus());
    dto.setDiscountMethod(discount.getDiscountMethod());
    dto.setDiscountAmount(discount.getDiscountAmount());
    dto.setStartDate(discount.getStartDate());
    dto.setEndDate(discount.getEndDate());

    return dto;
  }

  @Transactional(readOnly = true)
  public Page<Discount> findAll(String search, Pageable pageable) {
    log.debug("Request to get all Discounts");
    return discountRepository
        .findAllByStatusAndNameContaining(Constants.STATUS.ACTIVE, search, pageable);
  }

  @Transactional(readOnly = true)
  public DiscountResponse findById(Long id) {
    log.debug("Request to get Discount : {}", id);
    Discount discount = discountRepository.findByIdAndStatus(id, Constants.STATUS.ACTIVE);
    if (DataUtils.isNull(discount)) {
      throw new BadRequestAlertException("Chương trình khuyến mại không tồn tại", "discount",
          "exist");
    }

    return discountMapper.toDiscountDTO(discount);
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
