package com.datn.maguirestore.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
public class DiscountResponseDTO {
    private Long id;
    private Integer discountMethod;
    private BigDecimal discountAmount;
    private Instant startDate;
    private Instant endDate;
}
