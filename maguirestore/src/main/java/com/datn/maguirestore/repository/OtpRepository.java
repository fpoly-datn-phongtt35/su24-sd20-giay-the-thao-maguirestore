package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Otp findByLoginAndOtpCode(String login, String otpCode);

    Otp findByLogin(String login);

    void deleteByLogin(String login);
}
