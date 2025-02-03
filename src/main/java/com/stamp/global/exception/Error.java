package com.stamp.global.exception;

import org.springframework.http.HttpStatus;

public interface Error {
    HttpStatus getStatus();
    String getMessage();
}
