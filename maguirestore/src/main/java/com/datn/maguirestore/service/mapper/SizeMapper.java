package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.SizeDTO;
import com.datn.maguirestore.entity.Size;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SizeMapper extends EntityMapper<SizeDTO, Size>{
    @Mapping(target = "status", source = "status")
    SizeDTO toDto(Size size);

}
