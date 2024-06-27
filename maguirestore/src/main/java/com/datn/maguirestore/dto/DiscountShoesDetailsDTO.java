package com.datn.maguirestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountShoesDetailsDTO {

    private Long id;

    private BigDecimal discountAmount;

    private DiscountDTO discountId;

    private ShoesDTO shoes;

}
