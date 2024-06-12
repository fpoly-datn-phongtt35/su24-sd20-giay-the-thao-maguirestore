package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.CategoryDTO;
import com.datn.maguirestore.dto.SizeDTO;
import com.datn.maguirestore.entity.Category;
import com.datn.maguirestore.entity.Size;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category>{
//    @Mapping(target = "status", source = "status")
//    CategoryDTO toDto(Category category);

}
