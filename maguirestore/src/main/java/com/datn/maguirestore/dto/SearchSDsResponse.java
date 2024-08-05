package com.datn.maguirestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSDsResponse {

    private List<Long> sizeIds;
    private Long brandId;
    private BigDecimal startPrice;
    private BigDecimal endPrice;
}
