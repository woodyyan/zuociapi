package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseCommentRequest;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhraseCommentValidatorTest {

    private PhraseCommentValidator validator = new PhraseCommentValidator();

    @Test(expected = ErrorException.class)
    public void validate() {
        PhraseCommentRequest request = new PhraseCommentRequest();
        validator.validate(request);
    }
}