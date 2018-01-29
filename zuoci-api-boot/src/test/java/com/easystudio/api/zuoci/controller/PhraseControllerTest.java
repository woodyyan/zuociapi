package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.PhraseRequest;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.service.PhraseService;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
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
import static org.hamcrest.CoreMatchers.is;

@RunWith(EasyMockRunner.class)
public class PhraseControllerTest extends EasyMockSupport {

    @TestSubject
    private PhraseController controller = new PhraseController();

    @Mock
    private PhraseService service;

    @Mock
    private PhraseTranslator translator;

    @Test
    public void shouldGetPhrasesGivenLimit() throws Exception {
        Pageable page = new PageRequest(0, 20);
        Phrases phrases = new Phrases();
        List<PhraseData> data = new ArrayList<>();
        PhraseData phraseData = new PhraseData();
        phraseData.setContent("content");
        phraseData.setObjectId(123L);
        data.add(phraseData);
        phrases.setData(data);
        List<Phrase> content = new ArrayList<>();
        Page<Phrase> pagedPhrases = new PageImpl<>(content, page, 100);

        expect(service.searchPhrase(true, true, page)).andReturn(pagedPhrases);
        ResponseEntity<Phrases> response = new ResponseEntity<>(phrases, HttpStatus.OK);
        expect(translator.toPhraseResponse(pagedPhrases)).andReturn(response);

        replayAll();
        ResponseEntity<Phrases> actual = controller.searchPhrase(true, true, page);
        verifyAll();

        Assert.assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(actual.getBody().getData().size(), is(1));
        Assert.assertThat(actual.getBody().getData().get(0).getObjectId(), is(123L));
        Assert.assertThat(actual.getBody().getData().get(0).getContent(), is("content"));
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowErrorExceptionIfContentIsMissing() throws Exception {
        PhraseRequest phraserequest = new PhraseRequest();
        PhraseData data = new PhraseData();
        data.setAuthorId("123");
        phraserequest.setData(data);
        controller.createPhrase(phraserequest);
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowErrorExceptionIfAuthorIdIsMissing() throws Exception {
        PhraseRequest phraserequest = new PhraseRequest();
        PhraseData data = new PhraseData();
        data.setContent("content");
        phraserequest.setData(data);
        controller.createPhrase(phraserequest);
    }
}