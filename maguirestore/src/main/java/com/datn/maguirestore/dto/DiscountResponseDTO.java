package com.datn.maguirestore.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResponseDTO {

    private Long id;
    private String code;
    private String name;
    private Integer discountMethod;
    private BigDecimal discountAmount;

    private Integer status;

    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;

}
