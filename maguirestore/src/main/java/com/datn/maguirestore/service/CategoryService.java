package com.datn.maguirestore.service;


import com.datn.maguirestore.dto.CategoryDTO;
import com.datn.maguirestore.dto.SizeDTO;
import com.datn.maguirestore.entity.Category;
import com.datn.maguirestore.entity.Size;
import com.datn.maguirestore.repository.CategoryRepository;
import com.datn.maguirestore.repository.SizeRepository;
import com.datn.maguirestore.service.mapper.CategoryMapper;
import com.datn.maguirestore.service.mapper.SizeMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;


    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);

        Category category = categoryMapper.toEntity(categoryDTO);
        category.setStatus(1);
        category = categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    public CategoryDTO update(CategoryDTO categoryDTO) {
        log.debug("Request to update Size : {}", categoryDTO);

        categoryDTO.setStatus(1);

        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    public List<CategoryDTO> findAll() {
        log.debug("Request to get all Category with status = 1");

        List<CategoryDTO> ds = categoryRepository.findAll().stream()
                .map(categoryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
        return ds;
    }


    public Page<CategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sizes with status = 1");

        Page<Category> sizesWithStatus1 = categoryRepository.findByStatus(1, pageable);
        return sizesWithStatus1.map(categoryMapper::toDto);
    }

    public Page<CategoryDTO> findDelete(Pageable pageable) {
        log.debug("Request to get all Sizes with status = 0");

        Page<Category> sizesWithStatus1 = categoryRepository.findByStatus(0, pageable);
        return sizesWithStatus1.map(categoryMapper::toDto);
    }

    public Optional<CategoryDTO> findOne(Long id) {
        log.debug("Request to get Size : {}", id);
        return categoryRepository.findById(id).map(categoryMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Size : {}", id);

        categoryRepository
                .findById(id)
                .ifPresent(existingSize -> {
                    existingSize.setStatus(0);
                    categoryRepository.save(existingSize);
                });
    }


}
