package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

    @Query(
            value = "select distinct  s.* from `size` s \n" +
                    "join shoes_details sd on s.id  = sd.size_id \n" +
                    "where sd.shoes_id = :shoesId and sd.color_id = :colorId and s.status = :status",
            nativeQuery = true
    )
    List<Size> findByShoesIdAndColor(@Param("shoesId") Long shoesId, @Param("colorId") Long colorId, @Param("status") Integer status);

    Page<Size> findByStatus(int status, Pageable pageable);
    List<Size> findByStatus(int status );
}
