package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.AdminUserDTO;
import com.datn.maguirestore.entity.User;
import com.datn.maguirestore.repository.UserRepository;
import com.datn.maguirestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccountController {

    private final UserRepository userRepository;

    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final UserService userService;

    @GetMapping("/account")
    public AdminUserDTO getAccount() {
//        return userService
//                .getUserWithAuthorities()
//                .map(AdminUserDTO::new)
//                .orElseThrow(() -> new AccountResourceException("User could not be found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByLogin(userDetails.getUsername()).get();
        AdminUserDTO dto = new AdminUserDTO();
        dto.setLogin(user.getLogin());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());

        return dto;
    }
}
