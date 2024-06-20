package com.datn.maguirestore.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private Long id;
    private String login;
    private String email;
    private String role;
}