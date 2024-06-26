package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.Discount;
import com.datn.maguirestore.repository.custom.DiscountRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long>, DiscountRepositoryCustom {
    Page<Discount> findAllByStatusAndNameContaining(Integer status, String name, Pageable pageable);

    Discount findByIdAndStatus(Long id, Integer status);

}
