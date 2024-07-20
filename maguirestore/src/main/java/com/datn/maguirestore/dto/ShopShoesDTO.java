package com.datn.maguirestore.dto;

import java.math.BigDecimal;

public interface ShopShoesDTO {
    Integer getId();
    String getName();
    String getCode();
    String getBrandName();
    String getShoesCode();
    Integer getQuantity();
    BigDecimal getPrice();
    Integer getRating();
    Integer getShoes_id();
    Integer getBrand_id();
    Integer getColor_id();
    String getColor_name();
    String getSize_name();
    Integer getSize_id();
    String getDescription();
    String getPath();
    String getPaths();
    String getSizes();
    String getColor_ids();
    String getSize_ids();
    String getSize_names();
    String getColor_names();
    BigDecimal getDiscount_amount();
    Integer getDiscount_method();
    String getDiscount_name();
    BigDecimal getDiscount_amount_3_4();
}
