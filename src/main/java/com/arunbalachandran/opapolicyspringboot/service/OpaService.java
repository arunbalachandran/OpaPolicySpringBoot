package com.arunbalachandran.opapolicyspringboot.service;

import com.arunbalachandran.opapolicyspringboot.security.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OpaService {

    private final String OPA_INPUT = "input";
    private final String BEARER_PREFIX = "Bearer ";
    private final String ROLE = "role";
    private final String RESULT = "result";
    private final String ALLOW = "allow";

    @Value("${opa.url}")
    private String opaUrl;

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    private JWTService jwtService;


    private String preParseToken(String token) {
        return token.replace(BEARER_PREFIX, "");
    }

    // TODO: better exception handling
    public boolean checkPermission(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> input = new HashMap<>();
        // TODO: this can be cleaner
        input.put(OPA_INPUT, Map.of(ROLE, jwtService.extractClaim(preParseToken(token), val -> val.get(ROLE, List.class)).get(0)));
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(input, headers);
        log.info("Request is : {}", request);

        ResponseEntity<Map> response = restTemplate.postForEntity(
            opaUrl + "/v1/data/auth",
            request,
            Map.class
        );

        log.info("Response is : {}", response);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return Boolean.TRUE.equals(((Map<String, String>)response.getBody().get(RESULT)).get(ALLOW));
        }
        return false;
    }
} 