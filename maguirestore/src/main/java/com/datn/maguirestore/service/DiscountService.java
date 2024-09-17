package com.datn.maguirestore.service;

import com.datn.maguirestore.config.Constants;
import com.datn.maguirestore.dto.DiscountDTO;
import com.datn.maguirestore.dto.DiscountResponseDTO;
import com.datn.maguirestore.dto.DiscountSearchDTO;
import com.datn.maguirestore.dto.ShoesDTO;
import com.datn.maguirestore.entity.Brand;
import com.datn.maguirestore.entity.Discount;
import com.datn.maguirestore.entity.DiscountDetails;
import com.datn.maguirestore.entity.Shoes;
import com.datn.maguirestore.entity.ShoesDetails;
import com.datn.maguirestore.errors.BadRequestAlertException;
import com.datn.maguirestore.payload.request.DiscountCreateRequest;
import com.datn.maguirestore.payload.request.DiscountUpdateRequest;
import com.datn.maguirestore.payload.response.DiscountUpdateResponse;
import com.datn.maguirestore.repository.BrandRepository;
import com.datn.maguirestore.repository.DiscountDetailsRepository;
import com.datn.maguirestore.repository.DiscountRepository;
import com.datn.maguirestore.repository.ShoesDetailsRepository;
import com.datn.maguirestore.repository.ShoesRepository;
import com.datn.maguirestore.service.dto2.DiscountCreateDTO;
import com.datn.maguirestore.service.dto2.DiscountShoesDetailsDTO;
import com.datn.maguirestore.service.mapper.DiscountMapper;
import com.datn.maguirestore.service.mapper.ShoesMapper;
import com.datn.maguirestore.util.DataUtils;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DiscountService {

  private final DiscountRepository discountRepository;
  private final DiscountMapper discountMapper;
  private final ShoesMapper shoesMapper;
  private final String baseCode = "KM";
  private final DiscountDetailsRepository discountDetailsRepository;
  private final ShoesDetailsRepository shoesDetailsRepository;
  private final ShoesRepository shoesRepository;
  private final BrandRepository brandRepository;

  private static final String ENTITY_NAME = "discount";

  // new
  public DiscountResponseDTO save(DiscountCreateDTO discountDTO) {
    String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
    log.debug("Request to save Discount : {}", discountDTO);
    if (discountDTO.getStartDate().isAfter(discountDTO.getEndDate())) {
      throw new BadRequestAlertException("Ngày hiệu lực không được lớn hơn ngày hết hiệu lực",
          ENTITY_NAME, "date");
    }
    if (Constants.DISCOUNT_METHOD.TOTAL_PERCENT.equals(discountDTO.getDiscountMethod())) {
      if (discountDTO.getDiscountAmount().doubleValue() > 100
          || discountDTO.getDiscountAmount().doubleValue() <= 0) {
        throw new BadRequestAlertException("Số % giảm phải lớn hơn 0 và nhỏ hơn 100", ENTITY_NAME,
            "date");
      }
    } else if (Constants.DISCOUNT_METHOD.PER_PERCENT.equals(discountDTO.getDiscountMethod())) {
      for (DiscountShoesDetailsDTO discountShoesDetails : discountDTO.getDiscountShoesDetailsDTOS()) {
        if (DataUtils.isNull(discountShoesDetails.getDiscountAmount())) {
          throw new BadRequestAlertException("Giảm giá không được để trống", ENTITY_NAME, "date");
        }
        if (
            discountShoesDetails.getDiscountAmount().doubleValue() > 100 ||
                discountShoesDetails.getDiscountAmount().doubleValue() <= 0
        ) {
          throw new BadRequestAlertException("Số % giảm phải lớn hơn 0 và nhỏ hơn 100", ENTITY_NAME,
              "date");
        }
      }
    } else if (Constants.DISCOUNT_METHOD.TOTAL_MONEY.equals(discountDTO.getDiscountMethod())) {
      for (DiscountShoesDetailsDTO discountShoesDetails : discountDTO.getDiscountShoesDetailsDTOS()) {
        ShoesDetails shoesDetail = shoesDetailsRepository.getMinPrice(
            discountShoesDetails.getShoesDetails().getId());

        if (shoesDetail.getPrice().compareTo(discountDTO.getDiscountAmount()) < 0) {
          throw new BadRequestAlertException(
              "Số tiền giảm không được lớn hơn số tiền của giày"
                  + discountShoesDetails.getShoesDetails().getName(),
              ENTITY_NAME,
              "date"
          );
        }
      }
    } else {
      for (DiscountShoesDetailsDTO discountShoesDetails : discountDTO.getDiscountShoesDetailsDTOS()) {
        ShoesDetails shoesDetail = shoesDetailsRepository.getMinPrice(
            discountShoesDetails.getShoesDetails().getId());
        if (DataUtils.isNull(discountShoesDetails.getDiscountAmount())) {
          throw new BadRequestAlertException("Giảm giá không được để trống", ENTITY_NAME, "date");
        }
        if (shoesDetail.getPrice().compareTo(discountShoesDetails.getDiscountAmount()) < 0) {
          throw new BadRequestAlertException(
              "Số tiền giảm không được lớn hơn số tiền của giày"
                  + discountShoesDetails.getShoesDetails().getName(),
              ENTITY_NAME,
              "date"
          );
        }
      }
    }
    Discount discount = discountRepository.findByIdAndStatus(discountDTO.getId(),
        Constants.STATUS.ACTIVE);
    if (Objects.nonNull(discount)) {
      if (
          discount.getStartDate().isBefore(DataUtils.getCurrentDateTime()) &&
              discount.getEndDate().isAfter(DataUtils.getCurrentDateTime())
      ) {
        throw new BadRequestAlertException("Bạn không thể cập nhật chương trình khuyến mãi này!",
            ENTITY_NAME, "date");
      }
    }
    discount = discountMapper.toDiscountEntity(discountDTO);
    discount.setCode(generateCode());
    discount.setStatus(Constants.STATUS.ACTIVE);
    discount.setCreatedBy(loggedUser);
    discount.setStatus(0);
    discount.setLastModifiedBy(loggedUser);
    discountRepository.save(discount);
    List<DiscountDetails> discountShoesDetailsList = discountDTO
        .getDiscountShoesDetailsDTOS()
        .stream()
        .map(this::mapDiscountShoesDetails)
        .collect(Collectors.toList());
    for (DiscountDetails discountShoesDetails : discountShoesDetailsList) {
      discountShoesDetails.setLastModifiedBy(loggedUser);
      discountShoesDetails.setDiscount(discount);
      if (Objects.isNull(discountShoesDetails.getId())) {
        discountShoesDetails.setCreatedBy(loggedUser);
        discountShoesDetails.setStatus(Constants.STATUS.ACTIVE);
      }
      if (
          Constants.DISCOUNT_METHOD.TOTAL_MONEY.equals(discount.getDiscountMethod()) ||
              Constants.DISCOUNT_METHOD.TOTAL_PERCENT.equals(discount.getDiscountMethod())
      ) {
        discountShoesDetails.setDiscountAmount(discount.getDiscountAmount());
      }
    }
    for (DiscountDetails discountShoesDetails : discountShoesDetailsList) {
      DiscountDetails discountShoesDetails1 = discountDetailsRepository.findByShoesIdAndStatus(
          discountShoesDetails.getShoes().getId(),
          discountShoesDetails.getBrandId()
      );

      if (Objects.nonNull(discountShoesDetails1) && !Objects.equals(discountShoesDetails.getId(),
          discountShoesDetails1.getId())) {
        Brand brand = brandRepository.findByIdAndStatus(discountShoesDetails.getBrandId(),
            Constants.STATUS.ACTIVE);
        Shoes shoes = shoesRepository.findByIdAndStatus(discountShoesDetails.getShoes().getId(),
            Constants.STATUS.ACTIVE);

        throw new BadRequestAlertException(
            "Giày đã được sử dụng trong chương trình giảm giá khác! Mã: " +
                (shoes == null ? "" : shoes.getCode()) + " - " +
                (brand == null ? "" : brand.getName()),
            ENTITY_NAME, "used"
        );

      }
    }

    discountDetailsRepository.saveAll(discountShoesDetailsList);
    return discountMapper.toDto(discount);
  }

  private DiscountDetails mapDiscountShoesDetails(DiscountShoesDetailsDTO discountShoesDetailsDTO) {
    DiscountDetails discountDetails = new DiscountDetails();
    discountDetails.setShoes(shoesMapper.toEntity(
        (ShoesDTO) List.of(discountShoesDetailsDTO.getShoesDetails())));
    discountDetails.setDiscountAmount(discountShoesDetailsDTO.getDiscountAmount());
    discountDetails.setId(discountShoesDetailsDTO.getId());
    discountDetails.setStatus(discountShoesDetailsDTO.getStatus());
    discountDetails.setBrandId(discountShoesDetailsDTO.getBrandId());
    return discountDetails;
  }


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
    List<Long> discountId = listDiscountB.stream().map(Discount::getId)
        .collect(Collectors.toList());
    discountDetailsRepository.updateStatus(discountId, 0);
  }
}
