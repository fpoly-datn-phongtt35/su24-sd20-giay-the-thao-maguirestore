package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.AdminUserDTO;
import com.datn.maguirestore.dto.UserDTO;
import com.datn.maguirestore.entity.ERole;
import com.datn.maguirestore.entity.User;
import com.datn.maguirestore.payload.request.SignupRequest;
import com.datn.maguirestore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // sign up
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

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDTO findById(long id) {
        Optional<User> user = userRepository.findById(id);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.get().getId());
        userDTO.setLogin(user.get().getLogin());
        return userDTO;
    }

    public User createUser(AdminUserDTO userDTO) {
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
        //String encryptedPassword = passwordEncoder.encode(userDTO.getPasswordHash());
        //user.setPassword(encryptedPassword);
        //user.setActivationKey(null);
        user.setCreatedDate(Instant.now());
        user.setActivated(true);
        user.setRole(ERole.ROLE_USER);
        userRepository.save(user);
        //this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<AdminUserDTO> updateUser(AdminUserDTO userDTO) {
        return Optional
                .of(userRepository.findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    //this.clearUserCaches(user);
                    user.setLogin(userDTO.getLogin().toLowerCase());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setPhone(userDTO.getPhone());
                    if (userDTO.getEmail() != null) {
                        user.setEmail(userDTO.getEmail().toLowerCase());
                    }
                    //user.setImageUrl(userDTO.getImageUrl());
                    user.setActivated(true);
                    user.setAddress(userDTO.getAddress());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(userDTO.getDob(), formatter);
                    Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
                    user.setDOB(instant);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                })
                .map(AdminUserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository
                .findBylogin(login)
                .ifPresent(user -> {
                    user.setActivated(false);
                    userRepository.save(user);
                    log.debug("Deleted User: {}", user);
                });
    }

    @Transactional(readOnly = true)
    public Page<AdminUserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(AdminUserDTO::new);
    }

}
