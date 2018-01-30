package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.PhraseComment;
import com.easystudio.api.zuoci.model.CommentData;
import com.easystudio.api.zuoci.repository.PhraseCommentRepository;
import com.easystudio.api.zuoci.translator.PhraseCommentTranslator;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.expect;

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
}