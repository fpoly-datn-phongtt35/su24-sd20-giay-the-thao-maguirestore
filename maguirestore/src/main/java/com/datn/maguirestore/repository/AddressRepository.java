package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.Address;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {}
