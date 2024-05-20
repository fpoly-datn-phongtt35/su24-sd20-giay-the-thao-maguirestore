package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.UserDTO;
import com.datn.maguirestore.entity.User;
import com.datn.maguirestore.payload.request.UserUpdateRequest;
import com.datn.maguirestore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        // Tìm kiếm user theo username
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với tên đăng nhập: " + username));
    }

    public boolean resetPassword(String newPassword, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found userId: " + id));

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setLocation(user.getLocation());

        return userDTO;
    }

    @Transactional
    public User updateUser(UserUpdateRequest userUpdateRequest, User currentUser, User targetUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentRole = authentication.getAuthorities().iterator().next().getAuthority();

        if (currentRole.equals("ROLE_ADMIN") || currentUser.getId().equals(targetUser.getId())) {
            targetUser.setUsername(userUpdateRequest.getUsername());
            targetUser.setEmail(userUpdateRequest.getEmail());
            targetUser.setBirthDate(userUpdateRequest.getBirthDate());
            targetUser.setLocation(userUpdateRequest.getLocation());
            return userRepository.save(targetUser);
        } else {
            throw new AccessDeniedException("You do not have permission to update this user.");
        }
    }
//    @Transactional
//    public User updateUser(UserUpdateRequest userUpdateRequest, User currentUser) {
//        currentUser.setUsername(userUpdateRequest.getUsername());
//        currentUser.setEmail(userUpdateRequest.getEmail());
//        currentUser.setBirthDate(userUpdateRequest.getBirthDate());
//        currentUser.setLocation(userUpdateRequest.getLocation());
//
//        return userRepository.save(currentUser);
//    }

    @Transactional
    public void deleteUser(User user) {
        // Xóa user
        userRepository.delete(user);
    }

}
