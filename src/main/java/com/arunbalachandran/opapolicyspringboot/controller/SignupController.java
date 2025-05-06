package com.arunbalachandran.opapolicyspringboot.controller;

import com.arunbalachandran.opapolicyspringboot.dto.SignupRequest;
import com.arunbalachandran.opapolicyspringboot.dto.UserDTO;
import com.arunbalachandran.opapolicyspringboot.entity.Role;
import com.arunbalachandran.opapolicyspringboot.entity.User;
import com.arunbalachandran.opapolicyspringboot.service.UserAuthenticationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/signup")
public class SignupController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    /**
     * Create a new user in the system, persisting it to the database.
     *
     * @param signupRequest
     * @return
     */
    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> userSignup(@Valid @RequestBody SignupRequest signupRequest) {
        User createdUser = userAuthenticationService.signup(signupRequest, Role.REG_USER);
        log.debug("User created in the system : {}", createdUser.getId());
        return new ResponseEntity<>(UserDTO.mapToDto(createdUser), HttpStatus.CREATED);
    }
}
