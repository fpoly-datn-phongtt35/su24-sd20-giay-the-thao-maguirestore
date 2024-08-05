package com.datn.maguirestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDetailsDTO {

    private Long id;
    private Integer status;
    private DiscountDTO discount;
    private ShoesDTO shoes;
}
