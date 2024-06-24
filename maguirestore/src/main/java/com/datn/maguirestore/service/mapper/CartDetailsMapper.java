package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.CartDTO;
import com.datn.maguirestore.dto.CartDetailsDTO;
import com.datn.maguirestore.entity.Cart;
import com.datn.maguirestore.entity.CartDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CartDetailsMapper extends EntityMapper<CartDetailsDTO, CartDetails>{
    @Named("toCartDetailsEntity")
    @Mapping(target = "status", expression = "java(-1)")
    CartDetailsDTO toCartDetailsEntity(CartDetails cartDetails);
}
