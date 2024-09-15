package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE FeedBack f SET f.status = :status WHERE f.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    Integer countAllByStatus(Integer status);
}
