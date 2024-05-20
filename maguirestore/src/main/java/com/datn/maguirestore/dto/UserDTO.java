package com.datn.maguirestore.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private Date birthDate;
    private String image;
    private String job;
    private String location;
}
