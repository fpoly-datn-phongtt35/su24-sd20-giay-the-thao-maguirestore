package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.BrandDTO;
import com.datn.maguirestore.dto.ShoesDTO;
import com.datn.maguirestore.dto.ShoesDetailsDTO;
import com.datn.maguirestore.entity.Brand;
import com.datn.maguirestore.entity.Shoes;
import com.datn.maguirestore.entity.ShoesDetails;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShoesDetailsMapper extends EntityMapper<ShoesDetailsDTO, ShoesDetails>{
    ShoesDetailsMapper INSTANCE = Mappers.getMapper(ShoesDetailsMapper.class);

    @Named("toShoesDetailsEntity")
    @Mapping(target = "status", expression = "java(-1)")
    ShoesDetailsDTO toShoesDetailsEntity(ShoesDetails shoesDetails);

    @Mapping(target = "shoes", source = "shoes")
//    @Mapping(target = "brand", source = "brand", qualifiedByName = "brandId")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "tax", source = "tax")
    @Mapping(target = "importPrice", source = "importPrice")
    @Mapping(target = "description", source = "description")
        //    @Mapping(target = "images", ignore = true)
    ShoesDetailsDTO toDto(ShoesDetails s);

//    @Named(value = "toShoesCustomDTO")
//    ShoesDetailsCustomeDTO toShoesCustomDTO(ShoesDetails s);
//
//    @Named(value = "toShoesCustomDTOList")
//    @IterableMapping(qualifiedByName = "toShoesCustomDTO")
//    List<ShoesDetailsCustomeDTO> toShoesCustomDTO(List<ShoesDetails> shoesDetails);

//    @Named("shoesId")
//    @Mapping(target = "id", source = "id")
//    ShoesDTO toDtoShoesId(Shoes shoes);

//    @Named("brandId")
//    @Mapping(target = "id", source = "id")
//    BrandDTO toDtoBrandId(Brand brand);
}
