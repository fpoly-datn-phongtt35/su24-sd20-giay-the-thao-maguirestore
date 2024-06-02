package com.datn.maguirestore.service;

import com.datn.maguirestore.entity.Otp;
import com.datn.maguirestore.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {
    @Autowired
    private OtpRepository otpRepository;

    public Otp generateOtp(String login) {
        otpRepository.deleteByLogin(login);
        // Tạo mã OTP ngẫu nhiên
        String otpCode = generateRandomOtpCode();

        // Thiết lập thời gian hết hạn (5 phút từ thời điểm hiện tại)
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

        // Lưu OTP vào cơ sở dữ liệu
        Otp otp = new Otp();
        otp.setLogin(login);
        otp.setOtpCode(otpCode);
        otp.setExpirationTime(expirationTime);
        otpRepository.save(otp);

        return otp;
    }

    public void deleteOtpByLogin(String login) {
        Otp otp = otpRepository.findByLogin(login);
        if (otp != null) {
            otpRepository.delete(otp);
        }
    }

    public boolean verifyOtp(String login, String otpCode) {
        // Kiểm tra xem mã OTP có hợp lệ hay không
        Otp otp = otpRepository.findByLoginAndOtpCode(login, otpCode);
        if (otp != null && !otp.isExpired()) {
            // Xóa OTP sau khi xác thực thành công
            otpRepository.delete(otp);
            return true;
        }
        return false;
    }

    public String generateRandomOtpCode() {
        int otpLength = 6; // Độ dài của mã OTP
        String allowedChars = "0123456789";

        StringBuilder otp = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < otpLength; i++) {
            int index = random.nextInt(allowedChars.length());
            otp.append(allowedChars.charAt(index));
        }
        return otp.toString();
    }
}
