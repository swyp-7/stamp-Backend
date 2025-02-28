package com.stamp.api.attendance.exception;

import com.stamp.global.exception.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum QRCodeErrorCode implements Error {
    QR_GENERATOR_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "QR코드 생성중 에러가 발생하였습니다."),
    QR_NOT_EXIST_ERROR(HttpStatus.BAD_REQUEST, "생성된 QR코드가 없습니다."),

    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
return message;
    }
}
