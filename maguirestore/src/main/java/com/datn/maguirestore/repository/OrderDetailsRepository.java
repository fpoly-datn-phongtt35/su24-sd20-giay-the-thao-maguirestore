package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findAllByOrder_IdAndStatus(Long id, Integer active);
    List<OrderDetails> findAllByOrder_IdInAndStatus(List<Long> orderId, Integer active);
}
