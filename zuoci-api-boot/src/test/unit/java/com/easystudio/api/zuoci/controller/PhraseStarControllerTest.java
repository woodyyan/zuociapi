package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.CountResponse;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.PhraseStarRequest;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.service.StarredPhraseService;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import com.easystudio.api.zuoci.validate.PhraseStarValidator;
import javassist.NotFoundException;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;

@RunWith(EasyMockRunner.class)
public class PhraseStarControllerTest extends EasyMockSupport {

    @TestSubject
    private PhraseStarController controller = new PhraseStarController();

    @Mock
    private StarredPhraseService service;

    @Mock
    private PhraseTranslator translator;

    @Mock
    private PhraseStarValidator validator;

    @Test
    public void shouldAddStarGivenPhraseIdAndUserId() throws NotFoundException {
        Long phraseId = 1L;
        String userId = "abc";

        PhraseStarRequest request = new PhraseStarRequest();
        request.setUserId(userId);
        request.setPhraseId(phraseId);
        validator.validate(request);
        service.addStar(request);

        controller.addStar(request);
    }

    @Test
    public void shouldSearchStarGivenUserId() {
        String userId = "abc";
        Pageable page = new PageRequest(0, 20);

        Phrases phrases = new Phrases();
        phrases.getMeta().setPageSize(20);
        phrases.getMeta().setPageNumber(0);
        phrases.getMeta().setTotalPages(1);
        phrases.getMeta().setTotalElements(1);
        PhraseData data = new PhraseData();
        data.setObjectId(1L);
        phrases.getData().add(data);
        expect(service.searchStar(userId, page)).andReturn(phrases);

        replayAll();
        ResponseEntity<Phrases> actual = controller.searchStar(userId, page);
        verifyAll();

        Assert.assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(actual.getBody().getMeta().getTotalPages(), is(1L));
        Assert.assertThat(actual.getBody().getMeta().getTotalElements(), is(1L));
        Assert.assertThat(actual.getBody().getMeta().getPageSize(), is(20L));
        Assert.assertThat(actual.getBody().getMeta().getPageNumber(), is(0L));
        Assert.assertThat(actual.getBody().getData().size(), is(1));
        Assert.assertThat(actual.getBody().getData().get(0).getObjectId(), is(1L));
    }

    @Test
    public void shouldCountStarGivenUserId() {
        String userId = "abc";
        Long phraseId = 1L;

        expect(service.countStar(userId, phraseId)).andReturn(1L);

        replayAll();
        ResponseEntity<CountResponse> responseEntity = controller.countStar(userId, phraseId);
        verifyAll();

        Assert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(responseEntity.getBody().getCount(), is(1L));
    }

    @Test
    public void shouldDeleteStarGivenStarIdAndUserId() {
        String userId = "abc";
        Long objectId = 1L;

        controller.deleteStar(userId, objectId);
    }
}