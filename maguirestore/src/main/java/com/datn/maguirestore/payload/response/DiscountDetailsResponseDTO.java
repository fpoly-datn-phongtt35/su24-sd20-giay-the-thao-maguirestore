package com.datn.maguirestore.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDetailsResponseDTO {
    private Long id;
    private DiscountResponseDTO discount;
    private ShoesResponseDTO shoes;
    private Integer status;
}
