package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.FeedBackDTO;
import com.datn.maguirestore.entity.FeedBack;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedBackMapper extends EntityMapper<FeedBackDTO, FeedBack> {

}
