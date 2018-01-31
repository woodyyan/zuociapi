package com.easystudio.api.zuoci.model.error;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public final class ErrorBuilder {
    public static Error buildInvalidParameterError(String details) {
        Error error = new Error();
        error.setStatus(BAD_REQUEST.name());
        error.setTitle("Invalid Parameter");
        error.setDetails(details);
        return error;
    }

    public static Error buildNotFoundError(String details) {
        Error error = new Error();
        error.setStatus(HttpStatus.NOT_FOUND.name());
        error.setTitle("Not Found");
        error.setDetails(details);
        return error;
    }
}
