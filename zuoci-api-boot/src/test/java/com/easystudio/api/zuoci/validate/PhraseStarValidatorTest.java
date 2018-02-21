package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseStarRequest;
import org.junit.Test;

public class PhraseStarValidatorTest {

    private PhraseStarValidator validator = new PhraseStarValidator();

    @Test(expected = ErrorException.class)
    public void shouldThrowErrorExceptionGivenUserIdIsEmpty() {
        PhraseStarRequest request = new PhraseStarRequest();
        request.setPhraseId(1L);
        validator.validate(request);
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowErrorExceptionGivenPhraseIdIsInvalid() {
        PhraseStarRequest request = new PhraseStarRequest();
        request.setUserId("abc");
        request.setPhraseId(0L);
        validator.validate(request);
    }
}