package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoesRepository extends JpaRepository<Shoes, Long> {

    Shoes findByIdAndStatus(Long id, Integer status);
}
