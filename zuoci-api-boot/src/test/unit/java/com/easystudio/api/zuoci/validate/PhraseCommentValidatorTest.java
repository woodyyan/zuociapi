package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.CommentData;
import com.easystudio.api.zuoci.model.PhraseCommentRequest;
import org.junit.Test;

public class PhraseCommentValidatorTest {

    private PhraseCommentValidator validator = new PhraseCommentValidator();

    @Test(expected = ErrorException.class)
    public void shouldThrowExceptionWhenContentIsEmpty() {
        PhraseCommentRequest request = new PhraseCommentRequest();
        CommentData data = new CommentData();
        data.setCommentatorId("123");
        data.setPhraseId(2L);
        request.setData(data);
        validator.validate(request);
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowExceptionWhenCommentatorIdIsEmpty() {
        PhraseCommentRequest request = new PhraseCommentRequest();
        CommentData data = new CommentData();
        data.setContent("content");
        data.setPhraseId(1L);
        request.setData(data);
        validator.validate(request);
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowExceptionWhenPhraseDataIsNull() {
        PhraseCommentRequest request = new PhraseCommentRequest();
        validator.validate(request);
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowExceptionWhenPhraseIdIsEmpty() {
        PhraseCommentRequest request = new PhraseCommentRequest();
        CommentData data = new CommentData();
        data.setContent("content");
        data.setCommentatorId("123");
        request.setData(data);
        validator.validate(request);
    }
}