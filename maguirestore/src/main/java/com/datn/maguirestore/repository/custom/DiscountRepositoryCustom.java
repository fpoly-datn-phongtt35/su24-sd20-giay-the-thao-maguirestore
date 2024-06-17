package com.datn.maguirestore.repository.custom;

import com.datn.maguirestore.dto.DiscountSearchDTO;

import java.util.List;

public interface DiscountRepositoryCustom {
    List<DiscountSearchDTO> search(String searchText);
}
