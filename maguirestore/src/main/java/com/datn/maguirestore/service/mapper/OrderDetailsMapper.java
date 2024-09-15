package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.OrderDTO;
import com.datn.maguirestore.dto.OrderDetailsDTO;
import com.datn.maguirestore.dto.ShoesDetailsDTO;
import com.datn.maguirestore.entity.Order;
import com.datn.maguirestore.entity.OrderDetails;
import com.datn.maguirestore.entity.ShoesDetails;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper extends EntityMapper<OrderDetailsDTO, OrderDetails>{

    @Mapping(target = "order", source = "order", qualifiedByName = "orderId")
        //    @Mapping(target = "shoesDetails", source = "shoesDetails", qualifiedByName = "shoesDetailsId")
    OrderDetailsDTO toDto(OrderDetails s);

    @Named("orderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDTO toDtoOrderId(Order order);

    @Named("shoesDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoesDetailsDTO toDtoShoesDetailsId(ShoesDetails shoesDetails);
}
