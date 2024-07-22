package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.PaymentDTO;
import com.datn.maguirestore.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {}