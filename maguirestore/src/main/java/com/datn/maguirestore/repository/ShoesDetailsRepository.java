package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.ShoesDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoesDetailsRepository extends JpaRepository<ShoesDetails, Long> {
}
