package com.datn.maguirestore.payload.request;

import com.datn.maguirestore.entity.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Date dob;
    private String address;
    private String phone;
    private ERole role;
}
