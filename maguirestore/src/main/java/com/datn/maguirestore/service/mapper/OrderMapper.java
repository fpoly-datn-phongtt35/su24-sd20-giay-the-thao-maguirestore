package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.OrderCreateDTO;
import com.datn.maguirestore.dto.OrderDTO;
import com.datn.maguirestore.dto.OrderResDTO;
import com.datn.maguirestore.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
  //    @Mapping(target = "owner", source = "owner", qualifiedByName = "userId")
  OrderDTO toDto(Order s);

  @Named("toOrderEntity")
  Order toOrderEntity(OrderCreateDTO orderCreateDTO);

  @Named("toOderResDTO")
  OrderResDTO toOderResDTO(Order order);
}
