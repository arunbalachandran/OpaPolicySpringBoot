package com.arunbalachandran.opapolicyspringboot.service;

import com.arunbalachandran.opapolicyspringboot.entity.Token;
import com.arunbalachandran.opapolicyspringboot.repository.TokenRepository;
import com.arunbalachandran.opapolicyspringboot.security.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    private long getTimeDiffInMillis(Date d1, Date d2) {
        return d1.getTime() - d2.getTime();
    }

    public Token saveToken(String token, UUID userId, TokenType tokenType, Date expirationDate) {
        return tokenRepository.save(
                Token.builder()
                        .userId(userId.toString())
                        // .userId(userId)
                        .token(token)
                        .tokenType(tokenType)
                        .expiry(getTimeDiffInMillis(expirationDate, new Date(System.currentTimeMillis())))
                        .build());
    }
}
