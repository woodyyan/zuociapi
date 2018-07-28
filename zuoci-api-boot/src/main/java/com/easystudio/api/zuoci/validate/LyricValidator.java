package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.LyricRequest;
import com.easystudio.api.zuoci.model.error.Error;
import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.easystudio.api.zuoci.model.error.ErrorBuilder.buildInvalidParameterError;

@Component
public class LyricValidator {
    public void validate(LyricRequest lyricRequest) {
        if (Strings.isNullOrEmpty(lyricRequest.getData().getTitle())) {
            Error error = buildInvalidParameterError("Lyric title should not be empty.");
            throw new ErrorException(HttpStatus.BAD_REQUEST, error);
        }

        if (Strings.isNullOrEmpty(lyricRequest.getData().getContent())) {
            Error error = buildInvalidParameterError("Lyric content should not be empty.");
            throw new ErrorException(HttpStatus.BAD_REQUEST, error);
        }
    }
}
