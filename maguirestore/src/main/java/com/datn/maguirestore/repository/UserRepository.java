package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByLogin(String login);

    Boolean existsByLogin(String login);

    Boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    Optional<User> findOneByLogin(String login);

}
