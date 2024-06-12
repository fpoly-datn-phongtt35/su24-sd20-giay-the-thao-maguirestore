package com.datn.maguirestore.service.mapper;

import com.datn.maguirestore.dto.CategoryDTO;
import com.datn.maguirestore.entity.Category;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-10T15:47:21+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toEntity(CategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Category category = new Category();

        category.setLastModifiedBy( dto.getLastModifiedBy() );
        category.setLastModifiedDate( dto.getLastModifiedDate() );
        category.setId( dto.getId() );
        category.setCode( dto.getCode() );
        category.setName( dto.getName() );
        category.setStatus( dto.getStatus() );

        return category;
    }

    @Override
    public CategoryDTO toDto(Category entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( entity.getId() );
        categoryDTO.setCode( entity.getCode() );
        categoryDTO.setName( entity.getName() );
        categoryDTO.setStatus( entity.getStatus() );
        categoryDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        categoryDTO.setLastModifiedDate( entity.getLastModifiedDate() );

        return categoryDTO;
    }

    @Override
    public List<Category> toEntity(List<CategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Category> list = new ArrayList<Category>( dtoList.size() );
        for ( CategoryDTO categoryDTO : dtoList ) {
            list.add( toEntity( categoryDTO ) );
        }

        return list;
    }

    @Override
    public List<CategoryDTO> toDto(List<Category> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CategoryDTO> list = new ArrayList<CategoryDTO>( entityList.size() );
        for ( Category category : entityList ) {
            list.add( toDto( category ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Category entity, CategoryDTO dto) {
        if ( dto == null ) {
            return;
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
}
