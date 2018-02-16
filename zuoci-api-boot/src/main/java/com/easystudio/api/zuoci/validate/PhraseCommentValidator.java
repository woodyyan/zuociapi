package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseCommentRequest;
import com.easystudio.api.zuoci.model.error.Error;
import org.springframework.stereotype.Service;

import static com.easystudio.api.zuoci.model.error.ErrorBuilder.buildInvalidParameterError;
import static org.jadira.usertype.spi.utils.lang.StringUtils.isEmpty;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class PhraseCommentValidator {
    public void validate(Long phraseId, PhraseCommentRequest phraseCommentRequest) {
        if (phraseCommentRequest.getData() == null) {
            Error error = buildInvalidParameterError("Data should not be empty.");
            throw new ErrorException(BAD_REQUEST, error);
        }
        if (isEmpty(phraseCommentRequest.getData().getContent())) {
            Error error = buildInvalidParameterError("Content should not be empty.");
            throw new ErrorException(BAD_REQUEST, error);
        }
        if (isEmpty(phraseCommentRequest.getData().getCommentatorId())) {
            Error error = buildInvalidParameterError("Commentator Id should not be empty.");
            throw new ErrorException(BAD_REQUEST, error);
        }
        if (phraseCommentRequest.getData().getPhraseId() == null
                || phraseCommentRequest.getData().getPhraseId() <= 0) {
            Error error = buildInvalidParameterError("Phrase Id is invalid.");
            throw new ErrorException(BAD_REQUEST, error);
        }
        if (!phraseId.equals(phraseCommentRequest.getData().getPhraseId())) {
            Error error = buildInvalidParameterError("Phrase Id should not be the same.");
            throw new ErrorException(BAD_REQUEST, error);
        }
    }
}
