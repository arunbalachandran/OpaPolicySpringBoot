package com.arunbalachandran.opapolicyspringboot.controller;

import com.arunbalachandran.opapolicyspringboot.constants.Constants;
import com.arunbalachandran.opapolicyspringboot.dto.UserDTO;
import com.arunbalachandran.opapolicyspringboot.entity.User;
import com.arunbalachandran.opapolicyspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    /*
     Use the OPA Policy to check whether the API caller has permissions to view the list of users
     */
    @PreAuthorize("hasAuthorization(#authToken)")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> findAll(
            @RequestHeader(Constants.AUTHORIZATION_HEADER) String authToken
    ) {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users.stream().map(UserDTO::mapToDto).toList(), HttpStatus.OK);
    }
}
