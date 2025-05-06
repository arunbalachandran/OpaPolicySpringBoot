package com.arunbalachandran.opapolicyspringboot.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class SignupResponse {
    UUID id;
    String name;
    String email;
    String token;
}
