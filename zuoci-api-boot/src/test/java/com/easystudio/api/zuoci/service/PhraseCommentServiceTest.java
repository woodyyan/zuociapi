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

        expect(translator.toPhraseComment(data)).andReturn(phraseComment);
        expect(repository.save(phraseComment)).andReturn(phraseComment);

        replayAll();
        service.createComment(data);
        verifyAll();
    }

    @Test
    public void shouldSearchPhraseCommentsGivenPage() {
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
        expect(repository.findByPhraseId(objectId, page)).andReturn(pagedComments);

        replayAll();
        Page<PhraseComment> comments = service.searchComment(objectId, page);
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
        expect(repository.findOne(objectId)).andReturn(comment);
        repository.delete(objectId);

        replayAll();
        service.deleteComment(phraseId, objectId);
        verifyAll();
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowNotFoundExceptionWhenCommentObjectIdNotExists() {
        Long objectId = 123L;

        expect(repository.findOne(objectId)).andReturn(null);

        Long phraseId = 1L;
        service.deleteComment(phraseId, objectId);
    }
}