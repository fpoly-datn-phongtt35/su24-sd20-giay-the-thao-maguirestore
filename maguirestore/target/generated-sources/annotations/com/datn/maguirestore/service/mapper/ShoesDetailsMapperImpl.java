package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.ShoesDetailsDTO;
import com.datn.maguirestore.entity.ShoesDetails;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T09:40:31+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class ShoesDetailsMapperImpl implements ShoesDetailsMapper {

    @Override
    public ShoesDetails toEntity(ShoesDetailsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ShoesDetails shoesDetails = new ShoesDetails();

        shoesDetails.setCreatedBy( dto.getCreatedBy() );
        shoesDetails.setCreatedDate( dto.getCreatedDate() );
        shoesDetails.setLastModifiedBy( dto.getLastModifiedBy() );
        shoesDetails.setLastModifiedDate( dto.getLastModifiedDate() );
        shoesDetails.setId( dto.getId() );
        shoesDetails.setPrice( dto.getPrice() );
        shoesDetails.setImportPrice( dto.getImportPrice() );
        shoesDetails.setTax( dto.getTax() );
        shoesDetails.setQuantity( dto.getQuantity() );
        shoesDetails.setDescription( dto.getDescription() );
        shoesDetails.setStatus( dto.getStatus() );
        shoesDetails.setShoes( dto.getShoes() );
        shoesDetails.setSize( dto.getSize() );
        shoesDetails.setColor( dto.getColor() );

        return shoesDetails;
    }

    @Override
    public ShoesDetailsDTO toDto(ShoesDetails entity) {
        if ( entity == null ) {
            return null;
        }

        ShoesDetailsDTO shoesDetailsDTO = new ShoesDetailsDTO();

        shoesDetailsDTO.setId( entity.getId() );
        shoesDetailsDTO.setPrice( entity.getPrice() );
        shoesDetailsDTO.setImportPrice( entity.getImportPrice() );
        shoesDetailsDTO.setTax( entity.getTax() );
        shoesDetailsDTO.setQuantity( entity.getQuantity() );
        shoesDetailsDTO.setDescription( entity.getDescription() );
        shoesDetailsDTO.setStatus( entity.getStatus() );
        shoesDetailsDTO.setCreatedBy( entity.getCreatedBy() );
        shoesDetailsDTO.setCreatedDate( entity.getCreatedDate() );
        shoesDetailsDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        shoesDetailsDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        shoesDetailsDTO.setShoes( entity.getShoes() );
        shoesDetailsDTO.setSize( entity.getSize() );
        shoesDetailsDTO.setColor( entity.getColor() );

        return shoesDetailsDTO;
    }

    @Override
    public List<ShoesDetails> toEntity(List<ShoesDetailsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ShoesDetails> list = new ArrayList<ShoesDetails>( dtoList.size() );
        for ( ShoesDetailsDTO shoesDetailsDTO : dtoList ) {
            list.add( toEntity( shoesDetailsDTO ) );
        }

        return list;
    }

    @Override
    public List<ShoesDetailsDTO> toDto(List<ShoesDetails> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ShoesDetailsDTO> list = new ArrayList<ShoesDetailsDTO>( entityList.size() );
        for ( ShoesDetails shoesDetails : entityList ) {
            list.add( toDto( shoesDetails ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(ShoesDetails entity, ShoesDetailsDTO dto) {
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
        if ( dto.getPrice() != null ) {
            entity.setPrice( dto.getPrice() );
        }
        if ( dto.getImportPrice() != null ) {
            entity.setImportPrice( dto.getImportPrice() );
        }
        if ( dto.getTax() != null ) {
            entity.setTax( dto.getTax() );
        }
        if ( dto.getQuantity() != null ) {
            entity.setQuantity( dto.getQuantity() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getShoes() != null ) {
            entity.setShoes( dto.getShoes() );
        }
        if ( dto.getSize() != null ) {
            entity.setSize( dto.getSize() );
        }
        if ( dto.getColor() != null ) {
            entity.setColor( dto.getColor() );
        }
    }

    @Override
    public ShoesDetailsDTO toShoesDetailsEntity(ShoesDetails shoesDetails) {
        if ( shoesDetails == null ) {
            return null;
        }

        ShoesDetailsDTO shoesDetailsDTO = new ShoesDetailsDTO();

        shoesDetailsDTO.setId( shoesDetails.getId() );
        shoesDetailsDTO.setPrice( shoesDetails.getPrice() );
        shoesDetailsDTO.setImportPrice( shoesDetails.getImportPrice() );
        shoesDetailsDTO.setTax( shoesDetails.getTax() );
        shoesDetailsDTO.setQuantity( shoesDetails.getQuantity() );
        shoesDetailsDTO.setDescription( shoesDetails.getDescription() );
        shoesDetailsDTO.setCreatedBy( shoesDetails.getCreatedBy() );
        shoesDetailsDTO.setCreatedDate( shoesDetails.getCreatedDate() );
        shoesDetailsDTO.setLastModifiedBy( shoesDetails.getLastModifiedBy() );
        shoesDetailsDTO.setLastModifiedDate( shoesDetails.getLastModifiedDate() );
        shoesDetailsDTO.setShoes( shoesDetails.getShoes() );
        shoesDetailsDTO.setSize( shoesDetails.getSize() );
        shoesDetailsDTO.setColor( shoesDetails.getColor() );

        shoesDetailsDTO.setStatus( -1 );

        return shoesDetailsDTO;
    }
}
