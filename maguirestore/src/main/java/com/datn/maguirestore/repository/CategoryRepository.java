package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.Category;
import com.datn.maguirestore.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {



    Page<Category> findByStatus(int status, Pageable pageable);
}
