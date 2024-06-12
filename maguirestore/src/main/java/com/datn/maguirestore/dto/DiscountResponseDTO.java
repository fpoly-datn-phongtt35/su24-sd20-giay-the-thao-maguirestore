package com.datn.maguirestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResponseDTO {

    private Long id;
    private String code;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private Integer discountMethod;

    private BigDecimal discountAmount;

    private Integer discountStatus;
    private List<DiscountShoesDetailsDTO> discountShoesDetailsDTOS;
}
