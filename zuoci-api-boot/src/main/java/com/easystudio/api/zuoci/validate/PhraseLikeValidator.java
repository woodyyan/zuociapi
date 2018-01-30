package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseLikeRequest;
import com.easystudio.api.zuoci.model.error.Error;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.util.StringUtils.isEmpty;

@Service
public class PhraseLikeValidator {
    public void validate(PhraseLikeRequest phraseLikeRequest) {
        if (isEmpty(phraseLikeRequest.getUserId())) {
            Error error = buildInvalidParameterError("User Id is required.");
            throw new ErrorException(BAD_REQUEST, error);
        }
    }

    private Error buildInvalidParameterError(String details) {
        Error error = new Error();
        error.setStatus(BAD_REQUEST.name());
        error.setTitle("Bad request");
        error.setDetails(details);
        return error;
    }
}
