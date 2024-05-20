package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.UserDTO;
import com.datn.maguirestore.entity.User;
import com.datn.maguirestore.payload.request.UserUpdateRequest;
import com.datn.maguirestore.security.jwt.JwtUtils;
import com.datn.maguirestore.security.services.UserDetailsImpl;
import com.datn.maguirestore.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private HttpServletRequest request;

    // Lay User theo Id
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/getUserById/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> getUserById(@PathVariable Long id, Authentication authentication) {
        try {
            // Lấy thông tin user hiện tại
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long currentUserId = userDetails.getId();

            if (userDetails.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")) && !currentUserId.equals(id)) {
                return new ResponseEntity<>("You do not have permission to view this user's information", HttpStatus.FORBIDDEN);
            }

            UserDTO userDTO = userService.getUserById(id);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
    }

    // Update user
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest, Principal principal) {
        try {
            String currentUsername = principal.getName();
            User currentUser = userService.findByUsername(currentUsername);

            User targetUser = userService.findById(id);
            if (targetUser == null) {
                return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            }

            // Cập nhật thông tin người dùng
            User updatedUser = userService.updateUser(userUpdateRequest, currentUser, targetUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>("Access Denied.", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("Update failed.", HttpStatus.BAD_REQUEST);
        }
    }
//    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest userUpdateRequest,
//                                           Principal principal) {
//        try {
//            String currentUser = principal.getName();
//            User user = userService.findByUsername(currentUser);
//
//            User updatedUser = userService.updateUser(userUpdateRequest, user);
//            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Update failed.", HttpStatus.BAD_REQUEST);
//        }
//    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping
    public void deleteUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        userService.deleteUser(user);
    }
}
