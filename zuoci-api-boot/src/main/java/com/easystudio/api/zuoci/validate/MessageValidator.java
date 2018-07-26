package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.MessageRequest;
import com.easystudio.api.zuoci.model.error.Error;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import static com.easystudio.api.zuoci.model.error.ErrorBuilder.buildInvalidParameterError;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class MessageValidator {
    public void validate(MessageRequest messageRequest) {
        if (Strings.isNullOrEmpty(messageRequest.getData().getContent())) {
            Error error = buildInvalidParameterError("Content should not be empty.");
            throw new ErrorException(BAD_REQUEST, error);
        }
        if (Strings.isNullOrEmpty(messageRequest.getData().getText())) {
            Error error = buildInvalidParameterError("Text should not be empty.");
            throw new ErrorException(BAD_REQUEST, error);
        }
    }
}
