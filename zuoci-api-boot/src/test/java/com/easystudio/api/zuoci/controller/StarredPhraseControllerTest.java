package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.PagingMeta;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.service.StarredPhraseService;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import javassist.NotFoundException;
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
public class StarredPhraseControllerTest extends EasyMockSupport {

    @TestSubject
    private StarredPhraseController controller = new StarredPhraseController();

    @Mock
    private StarredPhraseService service;

    @Mock
    private PhraseTranslator translator;

    @Test
    public void shouldAddStarGivenPhraseIdAndUserId() throws NotFoundException {
        Long phraseId = 1L;
        String userId = "abc";

        service.addStar(phraseId, userId);

        controller.addStar(phraseId, userId);
    }

    @Test
    public void shouldSearchStarGivenUserId() {
        String userId = "abc";
        Pageable page = new PageRequest(0, 20);

        List<Phrase> content = new ArrayList<>();
        Page<Phrase> pagedPhrases = new PageImpl<>(content);
        expect(service.searchStar(userId, page)).andReturn(pagedPhrases);
        Phrases body = new Phrases();
        PagingMeta meta = new PagingMeta();
        meta.setTotalPages(1);
        meta.setTotalElements(1);
        meta.setPageSize(20);
        meta.setPageNumber(0);
        body.setMeta(meta);
        List<PhraseData> data = new ArrayList<>();
        PhraseData phraseData = new PhraseData();
        phraseData.setObjectId(1L);
        data.add(phraseData);
        body.setData(data);
        ResponseEntity<Phrases> entity = new ResponseEntity<>(body, HttpStatus.OK);
        expect(translator.toPhraseResponse(pagedPhrases)).andReturn(entity);

        replayAll();
        ResponseEntity<Phrases> phrases = controller.searchStar(userId, page);
        verifyAll();

        Assert.assertThat(phrases.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(phrases.getBody().getMeta().getTotalPages(), is(1L));
        Assert.assertThat(phrases.getBody().getMeta().getTotalElements(), is(1L));
        Assert.assertThat(phrases.getBody().getMeta().getPageSize(), is(20L));
        Assert.assertThat(phrases.getBody().getMeta().getPageNumber(), is(0L));
        Assert.assertThat(phrases.getBody().getData().size(), is(1));
        Assert.assertThat(phrases.getBody().getData().get(0).getObjectId(), is(1L));
    }

    @Test
    public void shouldDeleteStarGivenStarIdAndUserId() {
        String userId = "abc";
        Long objectId = 1L;

        controller.deleteStar(userId, objectId);
    }
}