package com.arunbalachandran.opapolicyspringboot.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiExceptionDTO {

    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;
}
