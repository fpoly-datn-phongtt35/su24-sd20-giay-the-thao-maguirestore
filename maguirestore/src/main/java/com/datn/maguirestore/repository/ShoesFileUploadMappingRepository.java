package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.ShoesDetails;
import com.datn.maguirestore.entity.ShoesFileUploadMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ShoesFileUploadMappingRepository extends JpaRepository<ShoesFileUploadMapping, Long> {

    List<ShoesFileUploadMapping> findByShoesDetails(ShoesDetails shoesDetails);

    Integer deleteAllByShoesDetailsId(Long id);
}
