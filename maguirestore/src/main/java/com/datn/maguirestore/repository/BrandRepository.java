package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.Address;
import com.datn.maguirestore.entity.Brand;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
  Brand findByIdAndStatus(Long id, int status);

  List<Brand> findByIdInAndStatus(List<Long> ids, int status);

  @Query(
      "SELECT b FROM Brand  b "
          + "WHERE (:keyword is null or :keyword = '' OR UPPER(b.name) LIKE CONCAT('%', UPPER(:keyword), '%') )")
  Page<Brand> findAll(String keyword, Pageable pageable);
}
