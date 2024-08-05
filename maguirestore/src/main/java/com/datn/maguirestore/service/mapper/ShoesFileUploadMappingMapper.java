package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.FileUploadDTO;
import com.datn.maguirestore.dto.ShoesDetailsDTO;
import com.datn.maguirestore.dto.ShoesFileUploadMappingDTO;
import com.datn.maguirestore.entity.FileUpload;
import com.datn.maguirestore.entity.ShoesDetails;
import com.datn.maguirestore.entity.ShoesFileUploadMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ShoesFileUploadMappingMapper extends EntityMapper<ShoesFileUploadMappingDTO, ShoesFileUploadMapping> {
    @Mapping(target = "fileUpload", source = "fileUpload", qualifiedByName = "fileUploadId")
    @Mapping(target = "shoesDetails", source = "shoesDetails", qualifiedByName = "shoesDetailsId")
    ShoesFileUploadMappingDTO toDto(ShoesFileUploadMapping s);

    @Named("fileUploadId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FileUpload toDtoFileUploadId(FileUpload fileUpload);

    @Named("shoesDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoesDetailsDTO toDtoShoesDetailsId(ShoesDetails shoesDetails);
}
