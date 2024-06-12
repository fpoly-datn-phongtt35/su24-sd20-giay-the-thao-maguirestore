package com.datn.maguirestore.payload.response;

import com.datn.maguirestore.entity.ERole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponse {
    private String login;
    private String email;
    private ERole role;

    @Override
    public String toString() {
        return "SignupResponse{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
