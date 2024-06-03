package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.ShoesDTO;
import com.datn.maguirestore.entity.Shoes;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

public interface ShoesMapper extends EntityMapper<ShoesDTO, Shoes>{
    @Named("toShoesEntity")
    @Mapping(target = "status", expression = "java(-1)")
    ShoesDTO toShoesEntity(Shoes shoes);
}
