package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseStarRequest;
import com.easystudio.api.zuoci.model.error.Error;
import org.springframework.stereotype.Component;

import static com.easystudio.api.zuoci.model.error.ErrorBuilder.buildInvalidParameterError;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class PhraseStarValidator {
    public void validate(PhraseStarRequest request) {
        if (isEmpty(request.getUserId())) {
            Error error = buildInvalidParameterError("User Id cannot be empty.");
            throw new ErrorException(BAD_REQUEST, error);
        }
        if (request.getPhraseId() == 0) {
            Error error = buildInvalidParameterError("Phrase Id is invalid.");
            throw new ErrorException(BAD_REQUEST, error);
        }
    }
}
