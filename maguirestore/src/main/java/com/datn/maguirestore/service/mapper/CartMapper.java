package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.CartDTO;
import com.datn.maguirestore.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CartMapper extends EntityMapper<CartDTO, Cart> {
    @Named("toCartEntity")
    @Mapping(target = "status", expression = "java(-1)")
    CartDTO toCartEntity(Cart cart);
}
