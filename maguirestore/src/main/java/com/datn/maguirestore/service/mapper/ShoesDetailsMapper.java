package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.ShoesDetailsDTO;
import com.datn.maguirestore.entity.ShoesDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ShoesDetailsMapper extends EntityMapper<ShoesDetailsDTO, ShoesDetails>{
    @Named("toShoesDetailsEntity")
    @Mapping(target = "status", expression = "java(-1)")
    ShoesDetailsDTO toShoesDetailsEntity(ShoesDetails shoesDetails);
}
