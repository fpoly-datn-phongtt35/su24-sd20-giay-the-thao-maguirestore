package com.datn.maguirestore.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDetaildResponseDTO {
    private Long id;
    private DiscountResponseDTO discount;
    private ShoesResponseDTO shoesDetails;
    private Integer status;
}
