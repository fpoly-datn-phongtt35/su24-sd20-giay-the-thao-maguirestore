package com.datn.maguirestore.service.dto2;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiscountDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private Integer discountMethod;

    private BigDecimal discountAmount;

    private Integer discountStatus;
}
