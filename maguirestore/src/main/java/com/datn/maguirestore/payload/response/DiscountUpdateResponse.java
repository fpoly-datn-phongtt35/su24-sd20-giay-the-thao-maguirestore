package com.datn.maguirestore.payload.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class DiscountUpdateResponse {
    private Long id;
    private String code;
    private String name;
    private Integer discountMethod;
    private BigDecimal discountAmount;

    private Integer status;

    private String lastModifiedBy;
    private Instant lastModifiedDate;
}
