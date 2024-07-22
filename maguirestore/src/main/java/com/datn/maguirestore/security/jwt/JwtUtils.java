package com.datn.maguirestore.security.jwt;

import com.datn.maguirestore.entity.ResetToken;
import com.datn.maguirestore.repository.ResetTokenRepository;
import com.datn.maguirestore.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author nguyenkhanhhoa
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${hoank17.app.jwtExpirationMs}")
    private Integer jwtExpirationMs;

    @Value("${hoank17.app.jwtExpChangePass}")
    private Integer jwtExpChangePass;

    private final ResetTokenRepository resetTokenRepository;

    @Getter
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // Lấy token để đổi password
    public String generateJwtTokenToChangePassword(String email) {
        String jwtToken = Jwts.builder()
            .setSubject(email)
            .claim("type", "password_reset")
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpChangePass))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();

        // Lưu trạng thái token đã tạo vào cơ sở dữ liệu
        ResetToken resetToken = new ResetToken(jwtToken, false);
        resetTokenRepository.save(resetToken);
        return jwtToken;
    }

    // Lấy token đăng nhập
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setHeaderParam("alg", "typ")
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long extractUserId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();

            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    // Lấy ra username từ token
    public String getSubjectFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}

