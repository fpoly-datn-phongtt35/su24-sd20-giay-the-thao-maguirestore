package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.FileUploadDTO;
import com.datn.maguirestore.entity.FileUpload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileUploadMapper extends EntityMapper<FileUploadDTO, FileUpload> {
}
