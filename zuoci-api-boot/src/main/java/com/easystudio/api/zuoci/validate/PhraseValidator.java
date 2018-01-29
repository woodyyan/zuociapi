package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseRequest;
import com.easystudio.api.zuoci.model.error.Error;
import org.springframework.data.geo.Point;

import java.awt.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.util.StringUtils.isEmpty;

public class PhraseValidator {
    public void validate(PhraseRequest phraseRequest) {
        if (isEmpty(phraseRequest.getData().getAuthorId())) {
            Error error = buildInvalidParameterError("authorId", "authorId is a required field");
            throw new ErrorException(BAD_REQUEST, error);
        }
        if (isEmpty(phraseRequest.getData().getContent())) {
            Error error = buildInvalidParameterError("content", "Content is a required field");
            throw new ErrorException(BAD_REQUEST, error);
        }
        if (phraseRequest.getData().getContent().length() > 50) {
            Error error = buildInvalidParameterError("content", "Content should less than 50");
            throw new ErrorException(BAD_REQUEST, error);
        }
        Point point = phraseRequest.getData().getPoint();
        if (point != null) {
            if (point.getX() >= 90) {
                Error error = buildInvalidParameterError("point", "Point x should less than 90");
                throw new ErrorException(BAD_REQUEST, error);
            }
            if (point.getY() >= 180) {
                Error error = buildInvalidParameterError("point", "Point y should less than 180");
                throw new ErrorException(BAD_REQUEST, error);
            }
        }
    }

    private Error buildInvalidParameterError(String parameterName, String details) {
        Error error = new Error();
        error.setStatus(BAD_REQUEST.name());
        error.setTitle("Bad request");
        error.setDetails(details);
        return error;
    }
}
