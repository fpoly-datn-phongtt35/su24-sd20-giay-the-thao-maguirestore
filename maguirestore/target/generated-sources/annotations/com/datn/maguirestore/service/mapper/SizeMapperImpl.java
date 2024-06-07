package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.SizeDTO;
import com.datn.maguirestore.entity.Size;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-07T23:13:30+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class SizeMapperImpl implements SizeMapper {

    @Override
    public Size toEntity(SizeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Size size = new Size();

        size.setCreatedBy( dto.getCreatedBy() );
        size.setCreatedDate( dto.getCreatedDate() );
        size.setLastModifiedBy( dto.getLastModifiedBy() );
        size.setLastModifiedDate( dto.getLastModifiedDate() );
        size.setId( dto.getId() );
        size.setCode( dto.getCode() );
        size.setName( dto.getName() );
        size.setStatus( dto.getStatus() );

        return size;
    }

    @Override
    public List<Size> toEntity(List<SizeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Size> list = new ArrayList<Size>( dtoList.size() );
        for ( SizeDTO sizeDTO : dtoList ) {
            list.add( toEntity( sizeDTO ) );
        }

        return list;
    }

    @Override
    public List<SizeDTO> toDto(List<Size> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SizeDTO> list = new ArrayList<SizeDTO>( entityList.size() );
        for ( Size size : entityList ) {
            list.add( toDto( size ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Size entity, SizeDTO dto) {
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
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
    }

    @Override
    public SizeDTO toDto(Size size) {
        if ( size == null ) {
            return null;
        }

        SizeDTO sizeDTO = new SizeDTO();

        sizeDTO.setStatus( size.getStatus() );
        sizeDTO.setId( size.getId() );
        sizeDTO.setCode( size.getCode() );
        sizeDTO.setName( size.getName() );
        sizeDTO.setCreatedBy( size.getCreatedBy() );
        sizeDTO.setCreatedDate( size.getCreatedDate() );
        sizeDTO.setLastModifiedBy( size.getLastModifiedBy() );
        sizeDTO.setLastModifiedDate( size.getLastModifiedDate() );

        return sizeDTO;
    }
}
