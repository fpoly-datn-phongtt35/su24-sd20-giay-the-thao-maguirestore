package com.datn.maguirestore.service;

import com.datn.maguirestore.entity.ERole;
import com.datn.maguirestore.entity.User;
import com.datn.maguirestore.payload.request.LoginRequest;
import com.datn.maguirestore.payload.request.SignupRequest;
import com.datn.maguirestore.payload.response.JwtResponse;
import com.datn.maguirestore.repository.UserRepository;
import com.datn.maguirestore.security.jwt.JwtUtils;
import com.datn.maguirestore.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;


    // Login service
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        try {
            // Thực hiện xác thực người dùng
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Thiết lập thông tin xác thực trong SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Tải thông tin người dùng bằng username
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

            // Tạo token JWT
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Lấy thông tin bổ sung của người dùng
            Long id = ((UserDetailsImpl) userDetails).getId();
            String username = userDetails.getUsername();
            String email = ((UserDetailsImpl) userDetails).getEmail();

            // Tạo JwtResponse
            return new JwtResponse(jwt, id, username, email);
        } catch (Exception e) {
            throw new RuntimeException("Sai mật khẩu hoặc tên đăng nhập", e);
        }
    }

    // Sign up
    public User signUp(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new IllegalArgumentException("User already exists");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));
        // Set default role as "user"
        user.setRole(ERole.ROLE_USER);
        userRepository.save(user);
        return user;
    }
}
