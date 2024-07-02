package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.DiscountDetailsDTO;
import com.datn.maguirestore.dto.DiscountShoesDetailsDTO;
import com.datn.maguirestore.entity.DiscountDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountShoesDetailsMapper extends EntityMapper<DiscountShoesDetailsDTO, DiscountDetails> {
    //    @Mapping(target = "discount", source = "discount", qualifiedByName = "discountId")
    //    @Mapping(target = "shoesDetails", source = "shoesDetails", qualifiedByName = "shoesDetailsId")
    DiscountShoesDetailsDTO toDto(DiscountDetails s);

    DiscountDetailsDTO convertDTO(DiscountDetails cd);
}
