package com.datn.maguirestore.controller;

import com.datn.maguirestore.config.Constants;
import com.datn.maguirestore.dto.AdminUserDTO;
import com.datn.maguirestore.dto.UserDTO;
import com.datn.maguirestore.entity.User;
import com.datn.maguirestore.errors.EmailAlreadyUsedException;
import com.datn.maguirestore.errors.LoginAlreadyUsedException;
import com.datn.maguirestore.repository.UserRepository;
import com.datn.maguirestore.security.jwt.JwtUtils;
import com.datn.maguirestore.security.services.UserDetailsImpl;
import com.datn.maguirestore.service.UserService;
import com.datn.maguirestore.util.HeaderUtil;
import com.datn.maguirestore.util.PaginationUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
            Arrays.asList(
                    "id",
                    "login",
                    "firstName",
                    "lastName",
                    "email",
                    "activated",
                    "createdBy",
                    "createdDate",
                    "lastModifiedBy",
                    "lastModifiedDate"
            )
    );

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserRepository userRepository;

    @Value("${clientApp.name}")
    private String applicationName;

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

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AdminUserDTO>> getAllUsers(Pageable pageable) {
        log.debug("REST request to get all User for an admin");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<AdminUserDTO> page = userService.getAllUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> createUser(@Valid @RequestBody AdminUserDTO userCreateRequest) throws URISyntaxException {

        if (userRepository.findByLogin(userCreateRequest.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findByEmail(userCreateRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userCreateRequest);

            UserDTO userDTO = new UserDTO();
            userDTO.setId(newUser.getId());
            userDTO.setLogin(newUser.getLogin());

            return ResponseEntity
                    .created(new URI("/api/v1/users/" + newUser.getLogin()))
                    .headers(
                            HeaderUtil.createAlert(applicationName, "A user is created with identifier " + newUser.getLogin(), newUser.getLogin())
                    )
                    .body(newUser);
        }
    }

    // Update user
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(value = "/update/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody AdminUserDTO userUpdateRequest, Principal principal) {
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
            return new ResponseEntity<>("You do not have permission to update this user.", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("Update failed.", HttpStatus.BAD_REQUEST);
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/users/{login}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable @Pattern(regexp = Constants.LOGIN_REGEX) String login) {
        userService.deleteUser(login);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createAlert(applicationName, "A user is deleted with identifier " + login, login))
                .build();
    }
}
