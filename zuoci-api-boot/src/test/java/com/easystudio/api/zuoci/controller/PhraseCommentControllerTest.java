package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.PhraseCommentRequest;
import com.easystudio.api.zuoci.service.PhraseCommentService;
import com.easystudio.api.zuoci.validate.PhraseCommentValidator;
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
import static org.hamcrest.Matchers.is;

@RunWith(EasyMockRunner.class)
public class PhraseCommentControllerTest extends EasyMockSupport {

    @TestSubject
    private PhraseCommentController controller = new PhraseCommentController();

    @Mock
    private PhraseCommentValidator validator;

    @Mock
    private PhraseCommentService service;

    @Test
    public void createComment() {
        PhraseCommentRequest request = new PhraseCommentRequest();

        validator.validate(request);
        service.createComment(request.getData());

        replayAll();
        ResponseEntity<?> responseEntity = controller.createComment(request);
        verifyAll();

        Assert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
    }
}