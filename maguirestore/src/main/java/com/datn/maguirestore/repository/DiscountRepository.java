package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.Discount;
import com.datn.maguirestore.repository.custom.DiscountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long>, DiscountRepositoryCustom {
    List<Discount> findAllByStatus(Integer status);

    Discount findByIdAndStatus(Long id, Integer status);

    @Query(value = "SELECT o.* FROM discount o WHERE o.created_date LIKE :date", nativeQuery = true)
    List<Discount> findByCreatedDate(@Param("date") String date);
}
