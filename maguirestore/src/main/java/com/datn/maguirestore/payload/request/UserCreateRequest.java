package com.datn.maguirestore.payload.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    private String login;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean activated = false;
}
