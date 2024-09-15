package com.datn.maguirestore.service;

import com.datn.maguirestore.config.Constants;
import com.datn.maguirestore.dto.*;
import com.datn.maguirestore.entity.Discount;
import com.datn.maguirestore.errors.BadRequestAlertException;
import com.datn.maguirestore.payload.request.DiscountCreateRequest;
import com.datn.maguirestore.payload.request.DiscountUpdateRequest;
import com.datn.maguirestore.payload.response.DiscountUpdateResponse;
import com.datn.maguirestore.repository.DiscountRepository;
import com.datn.maguirestore.repository.DiscountDetailsRepository;
import com.datn.maguirestore.service.mapper.DiscountMapper;
import com.datn.maguirestore.util.DataUtils;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DiscountService {

  private final DiscountRepository discountRepository;
  private final DiscountMapper discountMapper;
  private final String baseCode = "KM";
  private final DiscountDetailsRepository discountDetailsRepository;

  private static final String ENTITY_NAME = "discount";

  public DiscountDTO createDiscount(DiscountCreateRequest discountDTO) {
    String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();

    Discount discount = new Discount();
    discount.setCode(generateCode());
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

  public String generateCode() {
    Instant currentDateTime = DataUtils.getCurrentDateTime();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = formatter.format(LocalDate.ofInstant(currentDateTime, ZoneId.of("UTC")));
    String dateString = DataUtils.makeLikeStr(formattedDate);
    List<Discount> list = discountRepository.findByCreatedDate(dateString);
    int numberInDay = list.size() + 1;
    String code = DataUtils.replaceSpecialCharacters(formattedDate);
    return baseCode + code + numberInDay;
  }

  public DiscountUpdateResponse update(Long id, DiscountUpdateRequest discountDTO) {
    String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
    log.debug("Request to update Discount : {}", discountDTO);

    Discount discount = discountRepository.findById(id)
        .orElseThrow(
            () -> new BadRequestAlertException("Entity not found", ENTITY_NAME, "notfound"));

    discount.setCode(discountDTO.getCode());
    discount.setName(discountDTO.getName());
    discount.setStatus(discountDTO.getStatus());
    discount.setDiscountMethod(discountDTO.getDiscountMethod());
//    discount.setStartDate(discountDTO.getStartDate());
//    discount.setEndDate(discountDTO.getEndDate());
    discount.setDiscountAmount(discountDTO.getDiscountAmount());
    discount.setLastModifiedBy(loggedUser);
    discountRepository.save(discount);

    DiscountUpdateResponse dto = new DiscountUpdateResponse();
    dto.setId(discount.getId());
    dto.setCode(discount.getCode());
    dto.setName(discount.getName());
    dto.setStatus(discount.getStatus());
    dto.setDiscountMethod(discount.getDiscountMethod());
    dto.setDiscountAmount(discount.getDiscountAmount());
//    dto.setStartDate(discount.getStartDate());
//    dto.setEndDate(discount.getEndDate());

    return dto;
  }

  @Transactional(readOnly = true)
  public Page<Discount> findAll(String search, Pageable pageable) {
    log.debug("Request to get all Discounts");
    return discountRepository
        .findAllByStatusAndNameContaining(Constants.STATUS.ACTIVE, search, pageable);
  }

  @Transactional(readOnly = true)
  public DiscountResponseDTO findById(Long id) {
    log.debug("Request to get Discount : {}", id);
    Discount discount = discountRepository.findByIdAndStatus(id, Constants.STATUS.ACTIVE);
    if (DataUtils.isNull(discount)) {
      throw new BadRequestAlertException("Chương trình khuyến mại không tồn tại", "discount",
          "exist");
    }
    DiscountResponseDTO dto = discountMapper.toDiscountDTO(discount);
    System.out.println("????????????????");
    System.out.println(dto.getStartDate());
    System.out.println(dto.getEndDate());
    System.out.println("????????????????");

    return dto;
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
    this.discountDetailsRepository.updateStatus(Collections.singletonList(id), -1);
    discountRepository.save(discount);
  }

  public List<DiscountSearchDTO> search(String searchText) {
    return discountRepository.search(searchText);
  }

  public void scanDiscount() {
    List<Discount> listDiscountA = discountRepository.findAllActive();
    List<Discount> listDiscountB = discountRepository.findAllHetHan();
    List<Discount> listSave = new ArrayList<>();
    for (Discount discount : listDiscountA) {
      discount.setStatus(1);
      listSave.add(discount);
    }
    for (Discount discount : listDiscountB) {
      discount.setStatus(2);
      listSave.add(discount);
    }
    discountRepository.saveAll(listSave);
    List<Long> discountId = listDiscountB.stream().map(Discount::getId).collect(Collectors.toList());
    discountDetailsRepository.updateStatus(discountId, 0);
  }
}
