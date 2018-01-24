package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.PhraseRequest;
import com.easystudio.api.zuoci.service.PhraseService;
import org.junit.Test;

public class PhraseControllerTest {

    private PhraseController controller = new PhraseController();

    private PhraseService service;

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