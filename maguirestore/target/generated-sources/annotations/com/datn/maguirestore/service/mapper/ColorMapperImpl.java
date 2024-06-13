package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.ColorDTO;
import com.datn.maguirestore.entity.Color;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T09:40:32+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class ColorMapperImpl implements ColorMapper {

    @Override
    public Color toEntity(ColorDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Color color = new Color();

        color.setCreatedBy( dto.getCreatedBy() );
        color.setCreatedDate( dto.getCreatedDate() );
        color.setLastModifiedBy( dto.getLastModifiedBy() );
        color.setLastModifiedDate( dto.getLastModifiedDate() );
        color.setId( dto.getId() );
        color.setCode( dto.getCode() );
        color.setName( dto.getName() );
        color.setStatus( dto.getStatus() );

        return color;
    }

    @Override
    public ColorDTO toDto(Color entity) {
        if ( entity == null ) {
            return null;
        }

        ColorDTO colorDTO = new ColorDTO();

        colorDTO.setId( entity.getId() );
        colorDTO.setCode( entity.getCode() );
        colorDTO.setName( entity.getName() );
        colorDTO.setStatus( entity.getStatus() );
        colorDTO.setCreatedBy( entity.getCreatedBy() );
        colorDTO.setCreatedDate( entity.getCreatedDate() );
        colorDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        colorDTO.setLastModifiedDate( entity.getLastModifiedDate() );

        return colorDTO;
    }

    @Override
    public List<Color> toEntity(List<ColorDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Color> list = new ArrayList<Color>( dtoList.size() );
        for ( ColorDTO colorDTO : dtoList ) {
            list.add( toEntity( colorDTO ) );
        }

        return list;
    }

    @Override
    public List<ColorDTO> toDto(List<Color> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ColorDTO> list = new ArrayList<ColorDTO>( entityList.size() );
        for ( Color color : entityList ) {
            list.add( toDto( color ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Color entity, ColorDTO dto) {
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
    public ColorDTO toColorEntity(Color color) {
        if ( color == null ) {
            return null;
        }

        ColorDTO colorDTO = new ColorDTO();

        colorDTO.setId( color.getId() );
        colorDTO.setCode( color.getCode() );
        colorDTO.setName( color.getName() );
        colorDTO.setCreatedBy( color.getCreatedBy() );
        colorDTO.setCreatedDate( color.getCreatedDate() );
        colorDTO.setLastModifiedBy( color.getLastModifiedBy() );
        colorDTO.setLastModifiedDate( color.getLastModifiedDate() );

        colorDTO.setStatus( -1 );

        return colorDTO;
    }
}
