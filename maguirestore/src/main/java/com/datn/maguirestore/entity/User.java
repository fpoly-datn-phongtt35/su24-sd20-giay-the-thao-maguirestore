package com.datn.maguirestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username must not be blank")
    @Size(max = 50)
    private String username;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 120)
    @JsonIgnore
    private String password;

    private Date birthDate;
    private String location;

    @Enumerated(EnumType.STRING)
    private ERole role;

    public User(String username, String email, String encode) {
        this.username = username;
        this.email = email;
        this.password = encode;
    }

    //test
}
