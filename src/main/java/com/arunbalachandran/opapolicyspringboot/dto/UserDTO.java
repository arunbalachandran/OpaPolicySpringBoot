package com.arunbalachandran.opapolicyspringboot.dto;

import com.arunbalachandran.opapolicyspringboot.entity.Role;
import com.arunbalachandran.opapolicyspringboot.entity.User;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class UserDTO {
    UUID id;
    String name;
    String email;
    Role role;

    public static UserDTO mapToDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
