package com.easystudio.api.zuoci.model.error;

import java.util.List;

public class Errors {
    private final List<Error> errors;

    public Errors(List<Error> errors) {
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
