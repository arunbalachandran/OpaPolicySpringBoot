package com.arunbalachandran.opapolicyspringboot.service;

import com.arunbalachandran.opapolicyspringboot.entity.Token;
import com.arunbalachandran.opapolicyspringboot.security.TokenType;

import java.util.Date;
import java.util.UUID;

public interface TokenService {

    Token saveToken(String token, UUID id, TokenType tokenType, Date expirationDate);

}
