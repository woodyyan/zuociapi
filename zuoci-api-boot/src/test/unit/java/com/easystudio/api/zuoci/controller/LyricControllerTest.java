package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.LyricRequest;
import com.easystudio.api.zuoci.model.LyricResponse;
import com.easystudio.api.zuoci.service.LyricService;
import com.easystudio.api.zuoci.validate.LyricValidator;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.easymock.EasyMock.expect;

@RunWith(EasyMockRunner.class)
public class LyricControllerTest extends EasyMockSupport {

    @TestSubject
    private LyricController controller = new LyricController();

    @Mock
    private LyricService service;

    @Mock
    private LyricValidator validator;

    @Test
    public void shouldReturn201WhenCreateLyric() {
        LyricRequest request = new LyricRequest();

        LyricResponse lyricResponse = new LyricResponse();
        validator.validate(request);
        expect(service.createLyric(request.getData())).andReturn(lyricResponse);

        replayAll();
        ResponseEntity<LyricResponse> response = controller.createLyric(request);
        verifyAll();

        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }
}