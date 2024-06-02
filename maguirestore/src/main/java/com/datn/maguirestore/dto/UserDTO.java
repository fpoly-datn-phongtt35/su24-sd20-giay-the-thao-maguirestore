package com.datn.maguirestore.dto;

import com.datn.maguirestore.entity.User;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;

    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                "}";
    }
}
