package com.datn.maguirestore.dto;

import com.datn.maguirestore.config.Constants;
import com.datn.maguirestore.entity.ERole;
import com.datn.maguirestore.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * A DTO representing a user, with his authorities.
 */
@Getter
@Setter
public class AdminUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    private String password;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private boolean activated = false;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private String phone;

    private ERole role;
    private String dob;
    private String address;

    public AdminUserDTO() {

    }

    public AdminUserDTO(User user) {
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.activated = user.isActivated();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.phone = user.getPhone();
        this.role = user.getRole();
        this.address = user.getAddress();
        this.dob = String.valueOf(user.getDOB());
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "AdminUserDTO{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", activated=" + activated +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
