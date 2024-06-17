package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.DiscountShoesDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountShoesDetailsRepository extends JpaRepository<DiscountShoesDetails, Long> {

    List<DiscountShoesDetails> findAllByDiscount_IdAndStatus(Long idDiscount, Integer status);

    @Query(
            value = "select * from discount_shoes_details dsd \n" +
                    "join discount d on d.id = dsd.discount_id \n" +
                    "and d.start_date <= now() and d.end_date >= now() \n" +
                    "and d.status  = 1\n" +
                    "where dsd.shoes_details_id = :shoesId and dsd.brand_id = :brandId and dsd.status = 1",
            nativeQuery = true
    )
    DiscountShoesDetails findByShoesIdAndStatus(@Param("shoesId") Long idShoes, @Param("brandId") Long idBrand);

    @Modifying
    @Query(value = "update discount_shoes_details set status = :status where discount_id in :ids and status = 1", nativeQuery = true)
    void updateStatus(@Param("ids") List<Long> ids, @Param("status") Integer status);
}
