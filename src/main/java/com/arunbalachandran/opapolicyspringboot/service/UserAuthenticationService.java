package com.arunbalachandran.opapolicyspringboot.service;

import com.arunbalachandran.opapolicyspringboot.dto.AuthenticationRequest;
import com.arunbalachandran.opapolicyspringboot.dto.SignupRequest;
import com.arunbalachandran.opapolicyspringboot.dto.UserDTO;
import com.arunbalachandran.opapolicyspringboot.entity.Role;
import com.arunbalachandran.opapolicyspringboot.entity.User;
import com.arunbalachandran.opapolicyspringboot.exception.ApiException;
import com.arunbalachandran.opapolicyspringboot.exception.BadRequestException;
import com.arunbalachandran.opapolicyspringboot.security.TokenType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface UserAuthenticationService {
    Map<TokenType, String> generateAuthTokens(UserDetails user);
    HttpHeaders generateAuthHeaders(UserDetails user);
    User signup(SignupRequest request, Role role);
    ResponseEntity<UserDTO> authenticate(AuthenticationRequest request);
    HttpHeaders refreshToken(String refreshToken) throws ApiException;
    void invalidate(String token) throws BadRequestException;
}
