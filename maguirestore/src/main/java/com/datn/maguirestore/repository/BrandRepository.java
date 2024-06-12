package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByIdAndStatus(Long id, int status);
    List<Brand> findByIdInAndStatus(List<Long> ids, int status);
}
