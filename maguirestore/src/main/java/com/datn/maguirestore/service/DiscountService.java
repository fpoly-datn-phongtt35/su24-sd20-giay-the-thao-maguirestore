package com.datn.maguirestore.service;

import com.datn.maguirestore.config.Constants;
import com.datn.maguirestore.dto.*;
import com.datn.maguirestore.entity.Brand;
import com.datn.maguirestore.entity.Discount;
import com.datn.maguirestore.entity.DiscountShoesDetails;
import com.datn.maguirestore.entity.Shoes;
import com.datn.maguirestore.errors.BadRequestAlertException;
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
    private final DiscountShoesDetailsMapper discountShoesDetailsMapper;

    private final ShoesRepository shoesRepository;
    private final ShoesMapper shoesMapper;

    private static final String ENTITY_NAME = "discount";
    private final String baseCode = "KM";
    private final BrandRepository brandRepository;

    public DiscountDTO createDiscount(DiscountCreateDTO discountDTO) {
        log.debug("Request to save Discount : {}", discountDTO);

        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (discountDTO.getStartDate().isAfter(discountDTO.getEndDate())) {
            throw new BadRequestAlertException("Start date không được lớn hơn end date", ENTITY_NAME, "date");
        }
        if (Constants.DISCOUNT_METHOD.TOTAL_PERCENT.equals(discountDTO.getDiscountMethod())) {
            if (discountDTO.getDiscountAmount().doubleValue() <= 0 || discountDTO.getDiscountAmount().doubleValue() > 100) {
                throw new BadRequestAlertException("Số % giảm giá phải > 0 và < 100", ENTITY_NAME, "date");
            }
        }
//        else if (Constants.DISCOUNT_METHOD.PER_PERCENT.equals(discountDTO.getDiscountMethod())) {
//            for (DiscountShoesDetailsDTO discountShoesDetails : discountDTO.getDiscountShoesDetailsDTOS()) {
//                if (DataUtils.isNull(discountShoesDetails.getDiscountAmount())) {
//                    throw new BadRequestAlertException("Giảm giá không được để trống", ENTITY_NAME, "date");
//                }
//                if (discountShoesDetails.getDiscountAmount().doubleValue() > 100 ||
//                                discountShoesDetails.getDiscountAmount().doubleValue() <= 0) {
//                    throw new BadRequestAlertException("Số % giảm phải lớn hơn 0 và nhỏ hơn 100", ENTITY_NAME, "date");
//                }
//            }
//        }

        Discount discount = discountRepository.findByIdAndStatus(discountDTO.getId(), Constants.STATUS.ACTIVE);
        if (Objects.nonNull(discount)) {
            if (
                    discount.getStartDate().isBefore(DataUtils.getCurrentDateTime()) &&
                    discount.getEndDate().isAfter(DataUtils.getCurrentDateTime())
            ) {
                throw new BadRequestAlertException("Bạn không thể cập nhật chương trình khuyến mãi này!", ENTITY_NAME, "date");
            }
        }
        discount = discountMapper.toDiscountEntity(discountDTO);
        discount.setCode(discountDTO.getCode());
        discount.setStatus(Constants.STATUS.ACTIVE);
        discount.setDiscountStatus(0);
        discount.setLastModifiedBy(loggedUser);
        discountRepository.save(discount);
//        List<DiscountShoesDetails> discountShoesDetailsList = discountDTO
//                .getDiscountShoesDetailsDTOS()
//                .stream()
//                .map(this::mapDiscountShoesDetails)
//                .collect(Collectors.toList());
//        for (DiscountShoesDetails discountShoesDetails : discountShoesDetailsList) {
//            discountShoesDetails.setLastModifiedBy(loggedUser);
//            discountShoesDetails.setDiscount(discount);
//            if (Objects.isNull(discountShoesDetails.getId())) {
//                discountShoesDetails.setCreatedBy(loggedUser);
//                discountShoesDetails.setStatus(Constants.STATUS.ACTIVE);
//            }
//            if (
//                    Constants.DISCOUNT_METHOD.TOTAL_MONEY.equals(discount.getDiscountMethod()) ||
//                            Constants.DISCOUNT_METHOD.TOTAL_PERCENT.equals(discount.getDiscountMethod())
//            ) {
//                discountShoesDetails.setDiscountAmount(discount.getDiscountAmount());
//            }
//        }
//        for (DiscountShoesDetails discountShoesDetails : discountShoesDetailsList) {
//            DiscountShoesDetails discountShoesDetails1 = discountShoesDetailsRepository.findByShoesIdAndStatus(
//                    discountShoesDetails.getShoesDetails().getId(),
//                    discountShoesDetails.getBrandId()
//            );
//            if (Objects.nonNull(discountShoesDetails1) && !Objects.equals(discountShoesDetails.getId(), discountShoesDetails1.getId())) {
//                Brand brand = brandRepository.findByIdAndStatus(discountShoesDetails.getBrandId(), Constants.STATUS.ACTIVE);
//                Shoes shoes = shoesRepository.findByIdAndStatus(discountShoesDetails.getShoesDetails().getId(), Constants.STATUS.ACTIVE);
//                   throw new BadRequestAlertException(
//                        "Giày đã được sử dụng trong chương trình giảm giá khác! Mã: " +
//                                (shoes == null ? "" : shoes.getCode()) + " - " + (brand == null ? "" : brand.getName()),
//                                ENTITY_NAME, "used");
////                discountShoesDetails1.setStatus(Constants.STATUS.DELETE);
////                discountShoesDetailsRepository.save(discountShoesDetails1);
//            }
//            discountShoesDetails1.setStatus(Constants.STATUS.DELETE);
//            discountShoesDetailsRepository.save(discountShoesDetails1);
//        }
        //discountShoesDetailsRepository.saveAll(discountShoesDetailsList);
        return discountMapper.toDto(discount);
    }

    public DiscountDTO update(DiscountCreateDTO discountDTO, Long id) {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Request to update Discount : {}", discountDTO);
        if (discountDTO.getStartDate().isAfter(discountDTO.getEndDate())) {
            throw new BadRequestAlertException("Ngày hiệu lực không được lớn hơn ngày hết hiệu lực", ENTITY_NAME, "date");
        }
        if (Constants.DISCOUNT_METHOD.TOTAL_PERCENT.equals(discountDTO.getDiscountMethod())) {
            if (discountDTO.getDiscountAmount().doubleValue() > 100 || discountDTO.getDiscountAmount().doubleValue() <= 0) {
                throw new BadRequestAlertException("Số % giảm phải lớn hơn 0 và nhỏ hơn 100", ENTITY_NAME, "date");
            }
        }
//        else if (Constants.DISCOUNT_METHOD.PER_PERCENT.equals(discountDTO.getDiscountMethod())) {
//            for (DiscountShoesDetailsDTO discountShoesDetails : discountDTO.getDiscountShoesDetailsDTOS()) {
//                if (
//                        discountShoesDetails.getDiscountAmount().doubleValue() > 100 ||
//                                discountShoesDetails.getDiscountAmount().doubleValue() <= 0
//                ) {
//                    throw new BadRequestAlertException("Số % giảm phải lớn hơn 0 và nhỏ hơn 100", ENTITY_NAME, "date");
//                }
//            }
//        }
        Discount discount = discountRepository.findByIdAndStatus(id, Constants.STATUS.ACTIVE);
        if (
                discount.getStartDate().isBefore(DataUtils.getCurrentDateTime()) &&
                        discount.getEndDate().isAfter(DataUtils.getCurrentDateTime())
        ) {
            throw new BadRequestAlertException("Bạn không thể cập nhật chương trình khuyến mãi này!", ENTITY_NAME, "date");
        }
        discount = discountMapper.toDiscountEntity(discountDTO);
        discount.setId(id);
        discount.setLastModifiedBy(loggedUser);
        discountRepository.save(discount);
//        List<DiscountShoesDetails> discountShoesDetailsList = discountShoesDetailsMapper.toEntity(
//                discountDTO.getDiscountShoesDetailsDTOS()
//        );
//        discountShoesDetailsList.forEach(discountShoesDetails -> {
//            discountShoesDetails.setLastModifiedBy(loggedUser);
//        });
//        for (DiscountShoesDetails discountShoesDetails : discountShoesDetailsList) {
//            DiscountShoesDetails discountShoesDetails1 = discountShoesDetailsRepository.findByShoesIdAndStatus(
//                    discountShoesDetails.getShoesDetails().getId(),
//                    discountShoesDetails.getBrandId()
//            );
//            if (Objects.nonNull(discountShoesDetails1) && !Objects.equals(discountShoesDetails.getId(), discountShoesDetails1.getId())) {
//                throw new BadRequestAlertException("Giày đã được sử dụng trong chương trình giảm giá khác!", ENTITY_NAME, "used");
//            }
//        }
//        discountShoesDetailsRepository.saveAll(discountShoesDetailsList);
        return discountMapper.toDto(discount);
    }

    @Transactional(readOnly = true)
    public List<DiscountDTO> findAll() {
        log.debug("Request to get all Discounts");
        return discountRepository
                .findAllByStatus(Constants.STATUS.ACTIVE)
                .stream()
                .map(discountMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public DiscountResponseDTO findById(Long id) {
        log.debug("Request to get Discount : {}", id);
        Discount discount = discountRepository.findByIdAndStatus(id, Constants.STATUS.ACTIVE);
        if (DataUtils.isNull(discount)) {
            throw new BadRequestAlertException("Chương trình khuyến mại không tồn tại", "discount", "exist");
        }
        DiscountResponseDTO discountCreateDTO = discountMapper.toDiscountDTO(discount);
        List<DiscountShoesDetails> discountShoesDetailsList = discountShoesDetailsRepository.findAllByDiscount_IdAndStatus(
                id,
                Constants.STATUS.ACTIVE
        );
        Set<Long> brandId = discountShoesDetailsList.stream().map(DiscountShoesDetails::getBrandId).collect(Collectors.toSet());
        List<Brand> brands = brandRepository.findByIdInAndStatus(new ArrayList<Long>(brandId), Constants.STATUS.ACTIVE);
        List<DiscountShoesDetailsDTO> detailsDTOList = discountShoesDetailsMapper.toDto(discountShoesDetailsList);
        for (DiscountShoesDetailsDTO shoesDetails : detailsDTOList) {
            for (Brand brand : brands) {
                if (Objects.equals(brand.getId(), shoesDetails.getBrandId())) {
                    shoesDetails.setBrandName(brand.getName());
                    break;
                }
            }
        }
        discountCreateDTO.setDiscountShoesDetailsDTOS(detailsDTOList);
        return discountCreateDTO;
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
        discount.setDiscountStatus(2);
        discount.setLastModifiedBy(loggedUser);
        discount.setLastModifiedDate(Instant.now().plus(7, ChronoUnit.HOURS));
        this.discountShoesDetailsRepository.updateStatus(Collections.singletonList(id), -1);
        discountRepository.save(discount);
    }


    private DiscountShoesDetails mapDiscountShoesDetails(DiscountShoesDetailsDTO discountShoesDetailsDTO) {
        DiscountShoesDetails discountShoesDetails = new DiscountShoesDetails();
        discountShoesDetails.setShoesDetails(shoesMapper.toEntity(discountShoesDetailsDTO.getShoesDetails()));
        discountShoesDetails.setDiscountAmount(discountShoesDetailsDTO.getDiscountAmount());
        discountShoesDetails.setId(discountShoesDetailsDTO.getId());
        discountShoesDetails.setStatus(discountShoesDetailsDTO.getStatus());
        discountShoesDetails.setBrandId(discountShoesDetailsDTO.getBrandId());
        return discountShoesDetails;
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
}
