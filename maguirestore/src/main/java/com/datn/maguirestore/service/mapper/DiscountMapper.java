package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.DiscountDTO;
import com.datn.maguirestore.entity.Discount;
import com.datn.maguirestore.payload.request.DiscountUpdateRequest;
import com.datn.maguirestore.payload.response.DiscountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DiscountMapper extends EntityMapper<DiscountDTO, Discount> {

    @Named("toDiscountEntity")
    Discount toDiscountEntity(DiscountUpdateRequest updateDTO);

    @Named("toDiscountDTO")
    DiscountResponse toDiscountDTO(Discount discount);
}
