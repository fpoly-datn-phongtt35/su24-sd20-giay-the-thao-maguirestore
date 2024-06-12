package com.datn.maguirestore.payload.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyOtpRequest {
    private String login;
    private String otpCode;
}
