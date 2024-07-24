package com.datn.maguirestore.dto;

import java.math.BigDecimal;

public interface CartDetailDTO {

    Integer getId();
    Integer getIdsh();
    Integer getIdsz();
    Integer getIdc();
    Integer getIdb();
    Integer getQuantity();
    Integer getQuantityShoesDetail();
    Integer getStatus();
    Integer getShoesdetailid();
    String getPath();
    BigDecimal getPrice();
    String getNameshoes();
    String getNamesize();
    String getNamecolor();
    Integer getDiscountmethod();
    BigDecimal getDiscountamount_1_2();
    BigDecimal getDiscountamount_3_4();
}
