package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.CartDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, Long> {
    @Query("SELECT c " +
            "FROM CartDetails c " +
            "WHERE c.cart.id = :id")
    Page<CartDetails> getAllByCart(Long id, Pageable pageable);
}
