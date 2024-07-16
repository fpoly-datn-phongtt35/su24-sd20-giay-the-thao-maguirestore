package com.datn.maguirestore.dto;

import java.math.BigDecimal;

public interface ShoesDetailDTOCustom {

    String getName();
    String getPrice();
    Integer getIdShoe();
    Integer getIdSize();
    Integer getIdColor();
    Integer getIdBrand();
    String getPath();
    Integer getDiscountmethod();
    BigDecimal getDiscountamount_1_2();
    BigDecimal getDiscountamount_3_4();
}
