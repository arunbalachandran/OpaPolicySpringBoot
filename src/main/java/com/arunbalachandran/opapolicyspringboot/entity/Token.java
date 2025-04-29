package com.arunbalachandran.opapolicyspringboot.entity;

import com.arunbalachandran.opapolicyspringboot.security.TokenType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Token")
public class Token {

    @Id
    @GeneratedValue(generator = "UUID")
    UUID id;
    
    @Indexed
    // TODO: add support for UUID when storing userId
    String userId;
    // UUID userId;

    String token;
    
    TokenType tokenType;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    long expiry;
}
