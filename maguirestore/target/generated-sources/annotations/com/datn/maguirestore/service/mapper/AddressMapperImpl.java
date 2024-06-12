package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.AddressDTO;
import com.datn.maguirestore.entity.Address;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
<<<<<<< HEAD
    date = "2024-06-10T00:02:32+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
=======
    date = "2024-06-10T15:47:21+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
>>>>>>> cd18f09e0065587595e4278c64541e35594126c7
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address toEntity(AddressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Address address = new Address();

        address.setCreatedBy( dto.getCreatedBy() );
        address.setCreatedDate( dto.getCreatedDate() );
        address.setLastModifiedBy( dto.getLastModifiedBy() );
        address.setLastModifiedDate( dto.getLastModifiedDate() );
        address.setId( dto.getId() );
        address.setProvince( dto.getProvince() );
        address.setProvinceName( dto.getProvinceName() );
        address.setDistrict( dto.getDistrict() );
        address.setDistrictName( dto.getDistrictName() );
        address.setWard( dto.getWard() );
        address.setWardName( dto.getWardName() );
        address.setAddressDetails( dto.getAddressDetails() );
        address.setStatus( dto.getStatus() );

        return address;
    }

    @Override
    public AddressDTO toDto(Address entity) {
        if ( entity == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setId( entity.getId() );
        addressDTO.setProvince( entity.getProvince() );
        addressDTO.setProvinceName( entity.getProvinceName() );
        addressDTO.setDistrict( entity.getDistrict() );
        addressDTO.setDistrictName( entity.getDistrictName() );
        addressDTO.setWard( entity.getWard() );
        addressDTO.setWardName( entity.getWardName() );
        addressDTO.setAddressDetails( entity.getAddressDetails() );
        addressDTO.setStatus( entity.getStatus() );
        addressDTO.setCreatedBy( entity.getCreatedBy() );
        addressDTO.setCreatedDate( entity.getCreatedDate() );
        addressDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        addressDTO.setLastModifiedDate( entity.getLastModifiedDate() );

        return addressDTO;
    }

    @Override
    public List<Address> toEntity(List<AddressDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Address> list = new ArrayList<Address>( dtoList.size() );
        for ( AddressDTO addressDTO : dtoList ) {
            list.add( toEntity( addressDTO ) );
        }

        return list;
    }

    @Override
    public List<AddressDTO> toDto(List<Address> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AddressDTO> list = new ArrayList<AddressDTO>( entityList.size() );
        for ( Address address : entityList ) {
            list.add( toDto( address ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Address entity, AddressDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCreatedBy() != null ) {
            entity.setCreatedBy( dto.getCreatedBy() );
        }
        if ( dto.getCreatedDate() != null ) {
            entity.setCreatedDate( dto.getCreatedDate() );
        }
        if ( dto.getLastModifiedBy() != null ) {
            entity.setLastModifiedBy( dto.getLastModifiedBy() );
        }
        if ( dto.getLastModifiedDate() != null ) {
            entity.setLastModifiedDate( dto.getLastModifiedDate() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getProvince() != null ) {
            entity.setProvince( dto.getProvince() );
        }
        if ( dto.getProvinceName() != null ) {
            entity.setProvinceName( dto.getProvinceName() );
        }
        if ( dto.getDistrict() != null ) {
            entity.setDistrict( dto.getDistrict() );
        }
        if ( dto.getDistrictName() != null ) {
            entity.setDistrictName( dto.getDistrictName() );
        }
        if ( dto.getWard() != null ) {
            entity.setWard( dto.getWard() );
        }
        if ( dto.getWardName() != null ) {
            entity.setWardName( dto.getWardName() );
        }
        if ( dto.getAddressDetails() != null ) {
            entity.setAddressDetails( dto.getAddressDetails() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
    }
}
