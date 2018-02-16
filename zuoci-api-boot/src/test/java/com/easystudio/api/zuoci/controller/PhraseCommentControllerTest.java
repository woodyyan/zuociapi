package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.PhraseComment;
import com.easystudio.api.zuoci.model.PhraseCommentRequest;
import com.easystudio.api.zuoci.model.PhraseComments;
import com.easystudio.api.zuoci.service.PhraseCommentService;
import com.easystudio.api.zuoci.translator.PhraseCommentTranslator;
import com.easystudio.api.zuoci.validate.PhraseCommentValidator;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

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

    @Mock
    private PhraseCommentTranslator translator;

    @Test
    public void createComment() {
        PhraseCommentRequest request = new PhraseCommentRequest();
        Long phraseId = 1L;

        validator.validate(phraseId, request);
        service.createComment(request.getData());

        replayAll();
        ResponseEntity<?> responseEntity = controller.createComment(phraseId, request);
        verifyAll();

        Assert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    public void shouldSearchCommentGivenPage() {
        Pageable page = new PageRequest(0, 20);
        List<PhraseComment> content = new ArrayList<>();
        Page<PhraseComment> pagedComments = new PageImpl<>(content);
        PhraseComments phraseComments = new PhraseComments();
        ResponseEntity<PhraseComments> response = new ResponseEntity<>(phraseComments, HttpStatus.OK);
        Long objectId = 1L;

        expect(service.searchComment(objectId, page)).andReturn(pagedComments);
        expect(translator.toPhraseCommentResponse(pagedComments)).andReturn(response);

        replayAll();
        ResponseEntity<PhraseComments> comments = controller.searchComment(objectId, page);
        verifyAll();

        Assert.assertThat(comments.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void shouldDeleteCommentGivenObjectId() {
        Long objectId = 123L;
        Long phraseId = 1L;

        service.deleteComment(phraseId, objectId);

        controller.deleteComment(phraseId, objectId);
    }
}