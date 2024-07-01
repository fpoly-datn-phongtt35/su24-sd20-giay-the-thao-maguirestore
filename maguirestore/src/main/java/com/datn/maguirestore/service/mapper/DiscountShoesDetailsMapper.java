package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.DiscountDTO;
import com.datn.maguirestore.dto.DiscountDetailsDTO;
import com.datn.maguirestore.dto.DiscountShoesDetailsDTO;
import com.datn.maguirestore.dto.ShoesDTO;
import com.datn.maguirestore.entity.Discount;
import com.datn.maguirestore.entity.DiscountShoesDetails;
import com.datn.maguirestore.entity.Shoes;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DiscountShoesDetailsMapper extends EntityMapper<DiscountShoesDetailsDTO, DiscountShoesDetails> {
    //    @Mapping(target = "discount", source = "discount", qualifiedByName = "discountId")
    //    @Mapping(target = "shoesDetails", source = "shoesDetails", qualifiedByName = "shoesDetailsId")
    DiscountShoesDetailsDTO toDto(DiscountShoesDetails s);

    DiscountDetailsDTO convertDTO(DiscountShoesDetails cd);

    DiscountShoesDetails convertEntity(DiscountDetailsDTO dto);


    @Named("discountId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DiscountDTO toDtoDiscountId(Discount discount);

    @Named("shoesDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoesDTO toDtoShoesDetailsId(Shoes shoes);
}
