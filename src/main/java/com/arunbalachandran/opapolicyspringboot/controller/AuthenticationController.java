package com.arunbalachandran.opapolicyspringboot.controller;

import com.arunbalachandran.opapolicyspringboot.dto.*;
import com.arunbalachandran.opapolicyspringboot.exception.ApiException;
import com.arunbalachandran.opapolicyspringboot.exception.BadRequestException;
import com.arunbalachandran.opapolicyspringboot.service.UserAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @PostMapping(value = "/login", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> login(
            @Valid @RequestBody AuthenticationRequest authenticationRequest) {
        return userAuthenticationService.authenticate(authenticationRequest);
    }

    @PostMapping(value = "/refresh", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws ApiException {
        HttpHeaders responseHeaders = userAuthenticationService
                .refreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }

    @PostMapping(value = "/logout", consumes = "application/json")
    public ResponseEntity<AuthenticationResponse> logout(@Valid @RequestBody InvalidationRequest invalidationRequest) throws BadRequestException {
        String accessToken = invalidationRequest.getAccessToken();
        String refreshToken = invalidationRequest.getRefreshToken();
        if (accessToken != null) {
            userAuthenticationService.invalidate(accessToken);
        }
        if (refreshToken != null) {
            userAuthenticationService.invalidate(refreshToken);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
