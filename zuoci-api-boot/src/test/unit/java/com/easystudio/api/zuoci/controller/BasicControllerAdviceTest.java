package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.error.Error;
import com.easystudio.api.zuoci.model.error.Errors;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;

public class BasicControllerAdviceTest {
    private BasicControllerAdvice advice = new BasicControllerAdvice();

    @Test
    public void shouldHandleErrorException() {
        Error error = new Error();
        error.setTitle("Bad Request");
        error.setDetails("Bad Request");
        error.setStatus("400");
        ErrorException ex = new ErrorException(HttpStatus.BAD_REQUEST, error);
        ResponseEntity<Errors> entity = advice.handleException(ex);

        Assert.assertThat(entity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        Assert.assertThat(entity.getStatusCodeValue(), is(400));
        Assert.assertThat(entity.getBody().getErrors().size(), is(1));
        Assert.assertThat(entity.getBody().getErrors().get(0).getStatus(), is("400"));
        Assert.assertThat(entity.getBody().getErrors().get(0).getDetails(), is("Bad Request"));
        Assert.assertThat(entity.getBody().getErrors().get(0).getTitle(), is("Bad Request"));
    }
}