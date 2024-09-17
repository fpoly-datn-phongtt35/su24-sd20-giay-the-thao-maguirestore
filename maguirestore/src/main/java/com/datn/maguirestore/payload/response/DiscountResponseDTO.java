package com.datn.maguirestore.payload.response;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountResponseDTO {
    private Long id;
    private Integer discountMethod;
    private BigDecimal discountAmount;
    private Instant startDate;
    private Instant endDate;
    private Integer status;
}
