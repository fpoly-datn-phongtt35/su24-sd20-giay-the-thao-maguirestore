package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.BrandDTO;
import com.datn.maguirestore.entity.Brand;
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
public class BrandMapperImpl implements BrandMapper {

    @Override
    public Brand toEntity(BrandDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Brand brand = new Brand();

        brand.setCreatedBy( dto.getCreatedBy() );
        brand.setCreatedDate( dto.getCreatedDate() );
        brand.setLastModifiedBy( dto.getLastModifiedBy() );
        brand.setLastModifiedDate( dto.getLastModifiedDate() );
        brand.setId( dto.getId() );
        brand.setCode( dto.getCode() );
        brand.setName( dto.getName() );
        brand.setDescription( dto.getDescription() );
        brand.setStatus( dto.getStatus() );

        return brand;
    }

    @Override
    public BrandDTO toDto(Brand entity) {
        if ( entity == null ) {
            return null;
        }

        BrandDTO brandDTO = new BrandDTO();

        brandDTO.setId( entity.getId() );
        brandDTO.setCode( entity.getCode() );
        brandDTO.setName( entity.getName() );
        brandDTO.setDescription( entity.getDescription() );
        brandDTO.setStatus( entity.getStatus() );
        brandDTO.setCreatedBy( entity.getCreatedBy() );
        brandDTO.setCreatedDate( entity.getCreatedDate() );
        brandDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        brandDTO.setLastModifiedDate( entity.getLastModifiedDate() );

        return brandDTO;
    }

    @Override
    public List<Brand> toEntity(List<BrandDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Brand> list = new ArrayList<Brand>( dtoList.size() );
        for ( BrandDTO brandDTO : dtoList ) {
            list.add( toEntity( brandDTO ) );
        }

        return list;
    }

    @Override
    public List<BrandDTO> toDto(List<Brand> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BrandDTO> list = new ArrayList<BrandDTO>( entityList.size() );
        for ( Brand brand : entityList ) {
            list.add( toDto( brand ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Brand entity, BrandDTO dto) {
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
    public BrandDTO toBrandEntity(Brand brand) {
        if ( brand == null ) {
            return null;
        }

        BrandDTO brandDTO = new BrandDTO();

        brandDTO.setId( brand.getId() );
        brandDTO.setCode( brand.getCode() );
        brandDTO.setName( brand.getName() );
        brandDTO.setDescription( brand.getDescription() );
        brandDTO.setCreatedBy( brand.getCreatedBy() );
        brandDTO.setCreatedDate( brand.getCreatedDate() );
        brandDTO.setLastModifiedBy( brand.getLastModifiedBy() );
        brandDTO.setLastModifiedDate( brand.getLastModifiedDate() );

        brandDTO.setStatus( -1 );

        return brandDTO;
    }
}
