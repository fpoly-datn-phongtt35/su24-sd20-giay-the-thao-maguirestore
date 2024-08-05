package com.datn.maguirestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountSearchDTO {

    private Long id;
    private String code;
    private String name;
    private Integer discountMethod;
    private String discountMethodName;
    private String status;
    private Instant startDate;
    private Instant endDate;
    private Instant lastModifiedDate;
    private String lastModifiedBy;
}
