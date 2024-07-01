package com.datn.maguirestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTO implements Serializable {
    private Long id;
    private String code;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private Integer discountMethod;
    private BigDecimal discountAmount;
}
