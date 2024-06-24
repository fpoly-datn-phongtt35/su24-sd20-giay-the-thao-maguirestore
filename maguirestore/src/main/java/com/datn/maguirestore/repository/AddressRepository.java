package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(
            "SELECT a FROM Address  a "
                    + "WHERE (:keyword is null OR UPPER(a.provinceName) LIKE CONCAT('%', UPPER(:keyword), '%') OR UPPER(a.districtName) LIKE CONCAT('%', UPPER(:keyword), '%') OR UPPER(a.wardName) LIKE CONCAT('%', UPPER(:keyword), '%') OR UPPER(a.addressDetails) LIKE CONCAT('%', UPPER(:keyword), '%') )")
    Page<Address> findAll(
            String keyword,
            Pageable pageable);

}
