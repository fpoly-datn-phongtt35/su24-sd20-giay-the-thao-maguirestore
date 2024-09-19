package com.datn.maguirestore.service.dto2;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiscountShoesDetailsDTO implements Serializable {

    private Long id;

    private BigDecimal discountAmount;

    private DiscountDTO discount;

    private ShoesDTO shoesDetails;

    private Long brandId;

    private String brandName;

    private Integer status;
}
