package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select cart from Cart cart where cart.user.login = :username")
    List<Cart> findByOwnerIsCurrentUser(@Param("username") String username);

    Cart findByUserId(Long id);
}
