package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseLikeRequest;
import com.easystudio.api.zuoci.model.error.Error;
import org.springframework.stereotype.Service;

import static com.easystudio.api.zuoci.model.error.ErrorBuilder.buildInvalidParameterError;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.util.StringUtils.isEmpty;

@Service
public class PhraseLikeValidator {
    public void validate(Long phraseId, PhraseLikeRequest phraseLikeRequest) {
        if (!phraseLikeRequest.getPhraseId().equals(phraseId)) {
            Error error = buildInvalidParameterError("Phrase Ids are not match.");
            throw new ErrorException(BAD_REQUEST, error);
        }
        if (isEmpty(phraseLikeRequest.getUserId())) {
            Error error = buildInvalidParameterError("User Id is required.");
            throw new ErrorException(BAD_REQUEST, error);
        }
    }
}
