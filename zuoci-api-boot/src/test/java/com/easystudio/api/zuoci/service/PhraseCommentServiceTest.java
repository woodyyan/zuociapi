package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.PhraseComment;
import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.CommentData;
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

import java.util.ArrayList;
import java.util.List;

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
        PhraseComment phraseComment = new PhraseComment();
        phraseComment.setObjectId(1L);

        expect(translator.toPhraseComment(data)).andReturn(phraseComment);
        expect(repository.save(phraseComment)).andReturn(phraseComment);

        replayAll();
        PhraseComment comment = service.createComment(data);
        verifyAll();

        Assert.assertThat(comment.getObjectId(), is(1L));
    }

    @Test
    public void shouldSearchPhraseCommentsGivenPhraseIdAndPage() {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable page = new PageRequest(0, 20, sort);
        Long objectId = 1L;

        List<PhraseComment> content = new ArrayList<>();
        PhraseComment comment1 = new PhraseComment();
        comment1.setContent("content1");
        content.add(comment1);
        PhraseComment comment2 = new PhraseComment();
        comment2.setContent("content2");
        content.add(comment2);
        Page<PhraseComment> pagedComments = new PageImpl<>(content, page, 2);
        expect(repository.findByPhraseIdAndIsVisible(objectId, true, page)).andReturn(pagedComments);

        replayAll();
        Page<PhraseComment> comments = service.searchComment(objectId, null, true, page);
        verifyAll();

        Assert.assertThat(comments.getTotalElements(), is(2L));
        Assert.assertThat(comments.getTotalPages(), is(1));
        Assert.assertThat(comments.getNumberOfElements(), is(2));
        Assert.assertThat(comments.getNumber(), is(0));
        Assert.assertThat(comments.getContent().size(), is(2));
        Assert.assertThat(comments.getContent().get(0).getContent(), is("content1"));
        Assert.assertThat(comments.getContent().get(1).getContent(), is("content2"));
    }

    @Test
    public void shouldSearchPhraseCommentsGivenUserIdAndPage() {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable page = new PageRequest(0, 20, sort);
        String userId = "abc";

        List<PhraseComment> content = new ArrayList<>();
        PhraseComment comment1 = new PhraseComment();
        comment1.setContent("content1");
        content.add(comment1);
        PhraseComment comment2 = new PhraseComment();
        comment2.setContent("content2");
        content.add(comment2);
        Page<PhraseComment> pagedComments = new PageImpl<>(content, page, 2);
        expect(repository.findAllByRepliedUserIdOrPhraseAuthorId(userId, userId, page)).andReturn(pagedComments);

        replayAll();
        Page<PhraseComment> comments = service.searchComment(0L, userId, true, page);
        verifyAll();

        Assert.assertThat(comments.getTotalElements(), is(2L));
        Assert.assertThat(comments.getTotalPages(), is(1));
        Assert.assertThat(comments.getNumberOfElements(), is(2));
        Assert.assertThat(comments.getNumber(), is(0));
        Assert.assertThat(comments.getContent().size(), is(2));
        Assert.assertThat(comments.getContent().get(0).getContent(), is("content1"));
        Assert.assertThat(comments.getContent().get(1).getContent(), is("content2"));
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
        phraseComment.setContent("content");
        expect(repository.findOne(objectId)).andReturn(phraseComment);

        replayAll();
        PhraseComment comment = service.getComment(objectId);
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