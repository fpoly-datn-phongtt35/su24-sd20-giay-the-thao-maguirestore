package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.AdminUserDTO;
import com.datn.maguirestore.dto.UserDTO;
import com.datn.maguirestore.entity.ERole;
import com.datn.maguirestore.entity.User;
import com.datn.maguirestore.payload.request.SignupRequest;
import com.datn.maguirestore.repository.UserRepository;
import com.datn.maguirestore.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User signUp(SignupRequest signupRequest) {
        if (userRepository.existsByLogin(signupRequest.getLogin())) {
            throw new IllegalArgumentException("User already exists");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        // Create new user's account
        User user = new User(signupRequest.getLogin(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(ERole.ROLE_USER);
        user.setCreatedBy("system");
        userRepository.save(user);
        return user;
    }

    public User createUser(AdminUserDTO userDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(userDTO.getDob(), formatter);
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        user.setDOB(instant);
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }

        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setActivationKey(null);
        user.setCreatedDate(Instant.now());
        user.setActivated(true);
        user.setRole(ERole.ROLE_USER);
        userRepository.save(user);
        return user;
    }

    public User findByUsername(String login) {
        // Tìm kiếm user theo username
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với tên đăng nhập: " + login));
    }

    public boolean resetPassword(String newPassword, String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
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

    public Page<AdminUserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(AdminUserDTO::new);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found userId: " + id));

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());

        return userDTO;
    }

    @Transactional
    public User updateUser(AdminUserDTO userUpdateRequest, User currentUser, User targetUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentRole = authentication.getAuthorities().iterator().next().getAuthority();

        if (currentRole.equals("ROLE_ADMIN") || currentUser.getId().equals(targetUser.getId())) {
            targetUser.setEmail(userUpdateRequest.getEmail());
            targetUser.setFirstName(userUpdateRequest.getFirstName());
            targetUser.setLastName(userUpdateRequest.getLastName());
            targetUser.setPhone(userUpdateRequest.getPhone());
            targetUser.setActivated(!targetUser.isActivated());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(userUpdateRequest.getDob(), formatter);
            Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            targetUser.setDOB(instant);

            targetUser.setAddress(userUpdateRequest.getAddress());
            targetUser.setRole(userUpdateRequest.getRole());
            return userRepository.save(targetUser);
        } else {
            throw new AccessDeniedException("You do not have permission to update this user.");
        }
    }

//    public Optional<AdminUserDTO> updateUser(AdminUserDTO userDTO) {
//        return Optional
//                .of(userRepository.findById(userDTO.getId()))
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .map(user -> {
//                    user.setLogin(userDTO.getLogin().toLowerCase());
//                    user.setFirstName(userDTO.getFirstName());
//                    user.setLastName(userDTO.getLastName());
//                    user.setPhone(userDTO.getPhone());
//                    if (userDTO.getEmail() != null) {
//                        user.setEmail(userDTO.getEmail().toLowerCase());
//                    }
//                    user.setActivated(true);
//                    user.setAddress(userDTO.getAddress());
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                    LocalDate localDate = LocalDate.parse(userDTO.getDob(), formatter);
//                    Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
//                    user.setDOB(instant);
//                    user.setRole(userDTO.getRole());
//                    return user;
//                })
//                .map(AdminUserDTO::new);
//    }

    @Transactional
    public void deleteUser(String login) {
        userRepository
                .findByLogin(login)
                .ifPresent(user -> {
                    user.setActivated(false);
                    userRepository.save(user);
                });
    }

}
