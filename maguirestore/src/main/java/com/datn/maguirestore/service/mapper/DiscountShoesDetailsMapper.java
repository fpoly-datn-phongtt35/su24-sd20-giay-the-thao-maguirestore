package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.DiscountDetailsDTO;
import com.datn.maguirestore.dto.DiscountShoesDetailsDTO;
import com.datn.maguirestore.entity.DiscountShoesDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountShoesDetailsMapper extends EntityMapper<DiscountShoesDetailsDTO, DiscountShoesDetails> {
    //    @Mapping(target = "discount", source = "discount", qualifiedByName = "discountId")
    //    @Mapping(target = "shoesDetails", source = "shoesDetails", qualifiedByName = "shoesDetailsId")
    DiscountShoesDetailsDTO toDto(DiscountShoesDetails s);

    DiscountDetailsDTO convertDTO(DiscountShoesDetails cd);
}
