package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.AddressDTO;
import com.datn.maguirestore.entity.Address;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {}
