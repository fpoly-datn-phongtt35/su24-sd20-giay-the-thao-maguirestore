package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.ColorDTO;
import com.datn.maguirestore.entity.Color;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ColorMapper extends EntityMapper<ColorDTO, Color> {

    @Named("toColorEntity")
    @Mapping(target = "status", expression = "java(-1)")
    ColorDTO toColorEntity(Color color);
}
