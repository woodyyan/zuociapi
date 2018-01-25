package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.PhraseRequest;
import com.easystudio.api.zuoci.model.PhraseResponse;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.service.PhraseService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.catalina.connector.Response;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;

public class PhraseControllerTest {

    private PhraseController controller = new PhraseController();

    private PhraseService service;

    @Test
    public void shouldGetPhrasesGivenLimit() throws Exception {
        int limit = 20;

        ResponseEntity<Phrases> phrases = controller.searchPhrase(limit);

        Assert.assertThat(phrases.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(phrases.getBody().getData().size(), is(1));
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