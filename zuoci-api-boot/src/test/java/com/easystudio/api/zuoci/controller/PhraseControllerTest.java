package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.*;
import com.easystudio.api.zuoci.service.PhraseService;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import com.easystudio.api.zuoci.validate.PhraseValidator;
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

    @Mock
    private PhraseValidator validator;

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
        String authorId = "abc";

        expect(service.searchPhrase(true, true, authorId, page)).andReturn(pagedPhrases);
        ResponseEntity<Phrases> response = new ResponseEntity<>(phrases, HttpStatus.OK);
        expect(translator.toPhraseResponse(pagedPhrases)).andReturn(response);

        replayAll();
        ResponseEntity<Phrases> actual = controller.searchPhrase(true, true, authorId, page);
        verifyAll();

        Assert.assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(actual.getBody().getData().size(), is(1));
        Assert.assertThat(actual.getBody().getData().get(0).getObjectId(), is(123L));
        Assert.assertThat(actual.getBody().getData().get(0).getContent(), is("content"));
    }

    @Test
    public void shouldCreatePhraseGivenPhrase() {
        PhraseRequest phraseRequest = new PhraseRequest();
        PhraseData data = new PhraseData();
        data.setContent("content");
        data.setAuthorId("123");
        phraseRequest.setData(data);

        validator.validate(phraseRequest);

        ResponseEntity<?> actual = controller.createPhrase(phraseRequest);

        Assert.assertThat(actual.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    public void shouldDeletePhraseGivenPhraseId() {
        Long objectId = 123L;
        ResponseEntity<?> actual = controller.deletePhrase(objectId);

        Assert.assertThat(actual.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    public void shouldUpdatePhraseViewCountGivenViewCountRequest() {
        ViewCountRequest request = new ViewCountRequest();
        Long objectId = 123L;
        service.updateViewCount(objectId, request);
        ResponseEntity<?> responseEntity = controller.updateViewCount(objectId, request);
        Assert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    public void shouldGetPhraseCountGivenPhraseContentInToday() {
        String content = "content";

        expect(service.getPhraseCountByContentInToday(content)).andReturn(10L);

        replayAll();
        ResponseEntity<PhraseCountResponse> responseEntity = controller.getPhraseCountByContentInToday(content);
        verifyAll();

        Assert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(responseEntity.getBody().getContent(), is("content"));
        Assert.assertThat(responseEntity.getBody().getCount(), is(10L));
    }
}