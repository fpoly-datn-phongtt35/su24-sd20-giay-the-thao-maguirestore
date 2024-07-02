package com.datn.maguirestore.controller;

import com.datn.maguirestore.entity.Otp;
import com.datn.maguirestore.payload.request.LoginRequest;
import com.datn.maguirestore.payload.request.SignupRequest;
import com.datn.maguirestore.payload.request.VerifyOtpRequest;
import com.datn.maguirestore.payload.response.JwtResponse;
import com.datn.maguirestore.payload.response.OtpResponse;
import com.datn.maguirestore.payload.response.SignupResponse;
import com.datn.maguirestore.security.jwt.JwtUtils;
import com.datn.maguirestore.security.services.UserDetailsImpl;
import com.datn.maguirestore.security.services.UserDetailsServiceImpl;
import com.datn.maguirestore.service.EmailService;
import com.datn.maguirestore.service.OtpService;
import com.datn.maguirestore.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @CrossOrigin("http://localhost:4200/")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            otpService.deleteOtpByLogin(loginRequest.getLogin());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Otp otp = otpService.generateOtp(loginRequest.getLogin());
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(loginRequest.getLogin());
            String email = userDetails.getEmail();
            String subject = "Your OTP Code";
            String text = "Your OTP code is: " + otp.getOtpCode();
            emailService.sendOtpMessage(email, subject, text);
            return ResponseEntity.ok(new OtpResponse(otp.getOtpCode()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Sai mật khẩu hoặc tên đăng nhập");
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtpAndGenerateToken(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        boolean isOtpValid = otpService.verifyOtp(verifyOtpRequest.getLogin(), verifyOtpRequest.getOtpCode());

        if (isOtpValid) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(verifyOtpRequest.getLogin());
            String jwt = jwtUtils.generateJwtToken(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            Long id = ((UserDetailsImpl) userDetails).getId();
            String login = userDetails.getUsername();
            String email = ((UserDetailsImpl) userDetails).getEmail();
            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            JwtResponse jwtResponse = new JwtResponse(jwt, id, login, email, role);
            return ResponseEntity.ok(jwtResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP is invalid or has expired");
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        SignupResponse signupResponse = userService.signUp(signUpRequest);
        return ResponseEntity.ok(signupResponse);
    }
}