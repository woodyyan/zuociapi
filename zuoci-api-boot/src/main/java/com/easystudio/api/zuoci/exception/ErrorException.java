package com.easystudio.api.zuoci.exception;

import com.easystudio.api.zuoci.model.error.Error;
import org.springframework.http.HttpStatus;

public class ErrorException extends RuntimeException {
    private final HttpStatus status;
    private final Error error;

    public ErrorException(HttpStatus status, Error error) {
        this.status = status;
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Error getError() {
        return error;
    }
}
