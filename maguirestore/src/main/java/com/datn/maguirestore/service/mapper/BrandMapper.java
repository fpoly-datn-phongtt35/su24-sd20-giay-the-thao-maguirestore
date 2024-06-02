package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.BrandDTO;
import com.datn.maguirestore.entity.Brand;

import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface BrandMapper extends EntityMapper<BrandDTO, Brand> {
    @Named("toBrandEntity")
    @Mapping(target = "status", expression = "java(-1)")
    BrandDTO toBrandEntity(Brand brand);
}
