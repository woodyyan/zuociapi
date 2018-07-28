package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.LyricRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class LyricValidatorTest {
    private LyricValidator validator = new LyricValidator();

    @Test
    public void shouldThrewBadRequestExceptionWhenTitleIsEmpty() {
        LyricRequest request = new LyricRequest();
        try {
            validator.validate(request);
            Assert.assertTrue(false);
        } catch (ErrorException ex) {
            Assert.assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
            Assert.assertEquals("Lyric title should not be empty.", ex.getError().getDetails());
        }
    }

    @Test
    public void shouldThrewBadRequestExceptionWhenContentIsEmpty() {
        LyricRequest request = new LyricRequest();
        request.getData().setTitle("title");
        try {
            validator.validate(request);
            Assert.assertTrue(false);
        } catch (ErrorException ex) {
            Assert.assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
            Assert.assertEquals("Lyric content should not be empty.", ex.getError().getDetails());
        }
    }

    @Test
    public void shouldDoNothingWhenContentAndTitleIsNotEmpty() {
        LyricRequest request = new LyricRequest();
        request.getData().setTitle("title");
        request.getData().setContent("content");
        validator.validate(request);
    }
}