package com.datn.maguirestore.payload.response;

import com.datn.maguirestore.dto.BrandDTO;
import com.datn.maguirestore.dto.CategoryDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoesResponseDTO {
    private Long id;
    private String name;
    private BrandResponseDTO brand;
    private CategoryResponseDTO category;
}
