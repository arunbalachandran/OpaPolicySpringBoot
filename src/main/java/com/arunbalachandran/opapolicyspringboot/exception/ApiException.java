package com.arunbalachandran.opapolicyspringboot.exception;

public class ApiException extends Exception {
   
    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }
}
