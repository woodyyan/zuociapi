package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.MessageRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class MessageValidatorTest {
    @Test
    public void shouldThrowErrorExceptionWhenContentIsEmpty() {
        MessageValidator validator = new MessageValidator();

        MessageRequest request = new MessageRequest();

        try {
            validator.validate(request);
            Assert.assertFalse(true);
        } catch (ErrorException ex) {
            Assert.assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
            Assert.assertEquals("Content should not be empty.", ex.getError().getDetails());
        }
    }

    @Test
    public void shouldThrowErrorExceptionWhenTextIsEmpty() {
        MessageValidator validator = new MessageValidator();

        MessageRequest request = new MessageRequest();
        request.getData().setContent("content");

        try {
            validator.validate(request);
            Assert.assertFalse(true);
        } catch (ErrorException ex) {
            Assert.assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
            Assert.assertEquals("Text should not be empty.", ex.getError().getDetails());
        }
    }

    @Test
    public void shouldDoNothingGivenTextAndContent() {
        MessageValidator validator = new MessageValidator();

        MessageRequest request = new MessageRequest();
        request.getData().setContent("content");
        request.getData().setText("text");

        validator.validate(request);
    }
}