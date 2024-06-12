package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.DiscountCreateDTO;
import com.datn.maguirestore.dto.DiscountDTO;
import com.datn.maguirestore.dto.DiscountResponseDTO;
import com.datn.maguirestore.entity.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DiscountMapper extends EntityMapper<DiscountDTO, Discount> {

    @Named("toDiscountEntity")
    Discount toDiscountEntity(DiscountCreateDTO createDTO);

    @Named("toDiscountDTO")
    DiscountResponseDTO toDiscountDTO(Discount discount);
}
