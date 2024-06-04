package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.ShoesDTO;
import com.datn.maguirestore.entity.Shoes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T21:51:45+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class ShoesMapperImpl implements ShoesMapper {

    @Override
    public Shoes toEntity(ShoesDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Shoes shoes = new Shoes();

        shoes.setCreatedBy( dto.getCreatedBy() );
        shoes.setCreatedDate( dto.getCreatedDate() );
        shoes.setLastModifiedBy( dto.getLastModifiedBy() );
        shoes.setLastModifiedDate( dto.getLastModifiedDate() );
        shoes.setId( dto.getId() );
        shoes.setCode( dto.getCode() );
        shoes.setName( dto.getName() );
        shoes.setDescription( dto.getDescription() );
        shoes.setStatus( dto.getStatus() );

        return shoes;
    }

    @Override
    public ShoesDTO toDto(Shoes entity) {
        if ( entity == null ) {
            return null;
        }

        ShoesDTO shoesDTO = new ShoesDTO();

        shoesDTO.setId( entity.getId() );
        shoesDTO.setCode( entity.getCode() );
        shoesDTO.setName( entity.getName() );
        shoesDTO.setDescription( entity.getDescription() );
        shoesDTO.setStatus( entity.getStatus() );
        shoesDTO.setCreatedBy( entity.getCreatedBy() );
        shoesDTO.setCreatedDate( entity.getCreatedDate() );
        shoesDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        shoesDTO.setLastModifiedDate( entity.getLastModifiedDate() );

        return shoesDTO;
    }

    @Override
    public List<Shoes> toEntity(List<ShoesDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Shoes> list = new ArrayList<Shoes>( dtoList.size() );
        for ( ShoesDTO shoesDTO : dtoList ) {
            list.add( toEntity( shoesDTO ) );
        }

        return list;
    }

    @Override
    public List<ShoesDTO> toDto(List<Shoes> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ShoesDTO> list = new ArrayList<ShoesDTO>( entityList.size() );
        for ( Shoes shoes : entityList ) {
            list.add( toDto( shoes ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Shoes entity, ShoesDTO dto) {
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
        if ( dto.getCode() != null ) {
            entity.setCode( dto.getCode() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
    }

    @Override
    public ShoesDTO toShoesEntity(Shoes shoes) {
        if ( shoes == null ) {
            return null;
        }

        ShoesDTO shoesDTO = new ShoesDTO();

        shoesDTO.setId( shoes.getId() );
        shoesDTO.setCode( shoes.getCode() );
        shoesDTO.setName( shoes.getName() );
        shoesDTO.setDescription( shoes.getDescription() );
        shoesDTO.setCreatedBy( shoes.getCreatedBy() );
        shoesDTO.setCreatedDate( shoes.getCreatedDate() );
        shoesDTO.setLastModifiedBy( shoes.getLastModifiedBy() );
        shoesDTO.setLastModifiedDate( shoes.getLastModifiedDate() );

        shoesDTO.setStatus( -1 );

        return shoesDTO;
    }
}
