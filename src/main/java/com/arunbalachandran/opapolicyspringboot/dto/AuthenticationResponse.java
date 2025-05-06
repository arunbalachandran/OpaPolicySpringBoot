package com.arunbalachandran.opapolicyspringboot.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthenticationResponse {
    String accessToken;
    String refreshToken;
}