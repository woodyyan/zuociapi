package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.PhraseComment;
import com.easystudio.api.zuoci.model.CommentData;
import com.easystudio.api.zuoci.model.CountResponse;
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
        PhraseComment comment = new PhraseComment();
        CommentData data = new CommentData();

        validator.validate(request);
        expect(service.createComment(request.getData())).andReturn(comment);
        expect(translator.toCommentData(comment)).andReturn(data);

        replayAll();
        ResponseEntity<?> responseEntity = controller.createComment(request);
        verifyAll();

        Assert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
        Assert.assertThat(responseEntity.getBody(), is(data));
    }

    @Test
    public void shouldSearchCommentGivenPhraseIdAndPage() {
        Pageable page = new PageRequest(0, 20);
        List<PhraseComment> content = new ArrayList<>();
        Page<PhraseComment> pagedComments = new PageImpl<>(content);
        PhraseComments phraseComments = new PhraseComments();
        ResponseEntity<PhraseComments> response = new ResponseEntity<>(phraseComments, HttpStatus.OK);
        Long objectId = 1L;

        expect(service.searchComment(objectId, null, true, page)).andReturn(pagedComments);
        expect(translator.toPhraseCommentResponse(pagedComments)).andReturn(response);

        replayAll();
        ResponseEntity<PhraseComments> comments = controller.searchComment(objectId, null, true, page);
        verifyAll();

        Assert.assertThat(comments.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void shouldSearchCommentGivenUserIdAndPage() {
        Pageable page = new PageRequest(0, 20);
        List<PhraseComment> content = new ArrayList<>();
        Page<PhraseComment> pagedComments = new PageImpl<>(content);
        PhraseComments phraseComments = new PhraseComments();
        ResponseEntity<PhraseComments> response = new ResponseEntity<>(phraseComments, HttpStatus.OK);
        String userId = "abc";

        expect(service.searchComment(0L, userId, true, page)).andReturn(pagedComments);
        expect(translator.toPhraseCommentResponse(pagedComments)).andReturn(response);

        replayAll();
        ResponseEntity<PhraseComments> comments = controller.searchComment(0L, userId, true, page);
        verifyAll();

        Assert.assertThat(comments.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void shouldGetCommentGivenObjectId() {
        Long objectId = 1L;

        PhraseComment phraseComment = new PhraseComment();
        CommentData data = new CommentData();
        data.setContent("content");
        expect(service.getComment(objectId)).andReturn(phraseComment);
        expect(translator.toCommentData(phraseComment)).andReturn(data);

        replayAll();
        ResponseEntity<CommentData> comment = controller.getComment(objectId);
        verifyAll();

        Assert.assertThat(comment.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(comment.getBody().getContent(), is("content"));
    }

    @Test
    public void shouldDeleteCommentGivenObjectId() {
        Long objectId = 123L;

        service.deleteComment(objectId);

        controller.deleteComment(objectId);
    }

    @Test
    public void shouldCountCommentGivenPhraseId() {
        Long phraseId = 1L;

        expect(service.countComment(phraseId, null, true)).andReturn(1L);

        replayAll();
        ResponseEntity<CountResponse> responseEntity = controller.countComment(phraseId, null, true);
        verifyAll();

        Assert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(responseEntity.getBody().getCount(), is(1L));
    }
}