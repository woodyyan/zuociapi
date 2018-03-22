package com.easystudio.api.zuoci.model.error;

import org.junit.Assert;
import org.junit.Test;

public class ErrorBuilderTest {
    @Test
    public void shouldBuildInvalidParameterError() {
        Error error = ErrorBuilder.buildInvalidParameterError("message");

        Assert.assertEquals(error.getStatus(), "400");
        Assert.assertEquals(error.getTitle(), "Invalid Parameter");
        Assert.assertEquals(error.getDetails(), "message");
    }

    @Test
    public void shouldBuildNotFoundError() {
        Error error = ErrorBuilder.buildNotFoundError("message");

        Assert.assertEquals(error.getStatus(), "404");
        Assert.assertEquals(error.getTitle(), "Not Found");
        Assert.assertEquals(error.getDetails(), "message");
    }

    @Test
    public void shouldCreateErrorBuilder() {
        ErrorBuilder builder = new ErrorBuilder();
        Assert.assertNotNull(builder);
    }
}