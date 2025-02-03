package com.stamp.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stamp.global.exception.Error;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationResponse<T> {
    private final LocalDateTime localDateTime;
    private final Integer code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    public static ApplicationResponse<Void> ok() {
        return new ApplicationResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "SUCCESS",
                null
        );
    }

    public static <T> ApplicationResponse<T> ok(T data) {
        return new ApplicationResponse<>(
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "SUCCESS",
                data
        );
    }

    public static ApplicationResponse<Error> error(Error errorCode){
        return new ApplicationResponse<>(
                LocalDateTime.now(),
                errorCode.getStatus().value(),
                errorCode.getMessage(),
                null
        );
    }

    public static ApplicationResponse<Error> error(Error errorCode, String message){
        return new ApplicationResponse<>(
                LocalDateTime.now(),
                errorCode.getStatus().value(),
                message,
                null
        );
    }

}
