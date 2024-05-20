package com.datn.maguirestore.controller;

import com.datn.maguirestore.payload.request.LoginRequest;
import com.datn.maguirestore.payload.request.SignupRequest;
import com.datn.maguirestore.payload.response.JwtResponse;
import com.datn.maguirestore.payload.response.MessageResponse;
import com.datn.maguirestore.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("Dang ky thanh cong!"));
    }
}
