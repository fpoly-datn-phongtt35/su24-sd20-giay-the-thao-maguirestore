package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ShoesRepository extends JpaRepository<Shoes, Long> {

    @Query("select s from Shoes s where s.name like '%' + :keyname + '%' and s.category.id = :categoryId")
    public List<Shoes> findByFiter(String keyname, Long categoryId );

    @Query("select s from Shoes s where s.category.id = :categoryId")
    public List<Shoes> findByCategory(Long categoryId );


    Shoes findByIdAndStatus(Long id, Integer status);



}
