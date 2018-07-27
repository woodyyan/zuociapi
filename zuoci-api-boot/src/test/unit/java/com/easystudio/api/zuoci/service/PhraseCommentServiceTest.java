package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.PhraseComment;
import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.CommentData;
import com.easystudio.api.zuoci.model.PhraseComments;
import com.easystudio.api.zuoci.repository.PhraseCommentRepository;
import com.easystudio.api.zuoci.translator.PhraseCommentTranslator;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;

@RunWith(EasyMockRunner.class)
public class PhraseCommentServiceTest extends EasyMockSupport {

    @TestSubject
    private PhraseCommentService service = new PhraseCommentService();

    @Mock
    private PhraseCommentTranslator translator;

    @Mock
    private PhraseCommentRepository repository;

    @Test
    public void shouldCreatePhraseCommentGivenCommentData() {
        CommentData data = new CommentData();
        data.setObjectId(1L);
        PhraseComment phraseComment = new PhraseComment();

        expect(translator.toPhraseComment(data)).andReturn(phraseComment);
        expect(repository.save(phraseComment)).andReturn(phraseComment);
        expect(translator.toCommentData(phraseComment)).andReturn(data);

        replayAll();
        CommentData comment = service.createComment(data);
        verifyAll();

        Assert.assertThat(comment.getObjectId(), is(1L));
    }

    @Test
    public void shouldSearchPhraseCommentsGivenPhraseIdAndPage() {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable page = new PageRequest(0, 20, sort);
        Long objectId = 1L;

        List<PhraseComment> content = new ArrayList<>();
        Page<PhraseComment> pagedComments = new PageImpl<>(content, page, 2);
        expect(repository.findByPhraseIdAndIsVisible(objectId, true, page)).andReturn(pagedComments);
        PhraseComments phraseComments = new PhraseComments();
        CommentData data1 = new CommentData();
        data1.setContent("content1");
        phraseComments.getData().add(data1);
        CommentData data2 = new CommentData();
        data2.setContent("content2");
        phraseComments.getData().add(data2);
        phraseComments.getMeta().setTotalElements(2);
        phraseComments.getMeta().setTotalPages(1);
        phraseComments.getMeta().setPageSize(2);
        phraseComments.getMeta().setPageNumber(0);
        expect(translator.toPhraseComments(pagedComments)).andReturn(phraseComments);

        replayAll();
        PhraseComments comments = service.searchComment(objectId, null, true, page);
        verifyAll();

        Assert.assertThat(comments.getMeta().getTotalElements(), is(2L));
        Assert.assertThat(comments.getMeta().getTotalPages(), is(1L));
        Assert.assertThat(comments.getMeta().getPageSize(), is(2L));
        Assert.assertThat(comments.getMeta().getPageNumber(), is(0L));
        Assert.assertThat(comments.getData().size(), is(2));
        Assert.assertThat(comments.getData().get(0).getContent(), is("content1"));
        Assert.assertThat(comments.getData().get(1).getContent(), is("content2"));
    }

    @Test
    public void shouldSearchPhraseCommentsGivenUserIdAndPage() {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable page = new PageRequest(0, 20, sort);
        String userId = "abc";

        List<PhraseComment> content = new ArrayList<>();
        Page<PhraseComment> pagedComments = new PageImpl<>(content, page, 2);
        expect(repository.findAll((Specification<PhraseComment>) anyObject(), (Pageable) anyObject())).andReturn(pagedComments);
        PhraseComments phraseComments = new PhraseComments();
        CommentData data1 = new CommentData();
        data1.setContent("content1");
        phraseComments.getData().add(data1);
        CommentData data2 = new CommentData();
        data2.setContent("content2");
        phraseComments.getData().add(data2);
        phraseComments.getMeta().setPageNumber(0);
        phraseComments.getMeta().setPageSize(2);
        phraseComments.getMeta().setTotalPages(1);
        phraseComments.getMeta().setTotalElements(2);
        expect(translator.toPhraseComments(pagedComments)).andReturn(phraseComments);

        replayAll();
        PhraseComments comments = service.searchComment(0L, userId, true, page);
        verifyAll();

        Assert.assertThat(comments.getMeta().getTotalElements(), is(2L));
        Assert.assertThat(comments.getMeta().getTotalPages(), is(1L));
        Assert.assertThat(comments.getMeta().getPageSize(), is(2L));
        Assert.assertThat(comments.getMeta().getPageNumber(), is(0L));
        Assert.assertThat(comments.getData().size(), is(2));
        Assert.assertThat(comments.getData().get(0).getContent(), is("content1"));
        Assert.assertThat(comments.getData().get(1).getContent(), is("content2"));
    }

    @Test
    public void shouldDeletePhraseCommentGivenCommentObjectIdExists() {
        Long objectId = 123L;
        Long phraseId = 1L;

        PhraseComment comment = new PhraseComment();
        comment.setPhraseId(phraseId);
        expect(repository.findOne(objectId)).andReturn(comment);
        repository.delete(objectId);

        replayAll();
        service.deleteComment(objectId);
        verifyAll();
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowNotFoundExceptionWhenCommentObjectIdNotExists() {
        Long objectId = 123L;

        expect(repository.findOne(objectId)).andReturn(null);

        service.deleteComment(objectId);
    }

    @Test
    public void shouldGetCommentGivenObjectId() {
        Long objectId = 1L;

        PhraseComment phraseComment = new PhraseComment();
        CommentData data = new CommentData();
        data.setContent("content");
        expect(repository.findOne(objectId)).andReturn(phraseComment);
        expect(translator.toCommentData(phraseComment)).andReturn(data);

        replayAll();
        CommentData comment = service.getComment(objectId);
        verifyAll();

        Assert.assertThat(comment.getContent(), is("content"));
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowErrorExceptionWhenPhraseIdIsNotSameWithComment() {
        Long objectId = 123L;

        PhraseComment comment = new PhraseComment();
        comment.setPhraseId(2L);
        expect(repository.findOne(objectId)).andReturn(comment);

        service.deleteComment(objectId);
    }

    @Test
    public void shouldCountCommentGivenPhraseId() {
        Long phraseId = 1L;

        expect(repository.countByPhraseId(phraseId, true)).andReturn(2L);

        replayAll();
        Long count = service.countComment(phraseId, null, true);
        verifyAll();

        Assert.assertThat(count, is(2L));
    }

    @Test
    public void shouldCountCommentGivenUserId() {
        String userId = "abc";

        expect(repository.countByUserId(userId, true)).andReturn(2L);

        replayAll();
        Long count = service.countComment(0L, userId, true);
        verifyAll();

        Assert.assertThat(count, is(2L));
    }
}