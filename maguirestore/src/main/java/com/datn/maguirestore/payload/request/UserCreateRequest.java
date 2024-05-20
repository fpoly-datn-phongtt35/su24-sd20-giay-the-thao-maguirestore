package com.datn.maguirestore.payload.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    private String username;
    private String password;
    private String email;
    private Date birthDate;
    private String location;
}
