package com.easystudio.api.zuoci.validate;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.PhraseRequest;
import org.junit.Test;
import org.springframework.data.geo.Point;

public class PhraseValidatorTest {

    private PhraseValidator validator = new PhraseValidator();

    @Test(expected = ErrorException.class)
    public void shouldThrowErrorExceptionIfContentIsMissing() throws Exception {
        PhraseRequest phraserequest = new PhraseRequest();
        PhraseData data = new PhraseData();
        data.setAuthorId("123");
        phraserequest.setData(data);
        validator.validate(phraserequest);
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowErrorExceptionIfAuthorIdIsMissing() throws Exception {
        PhraseRequest phraserequest = new PhraseRequest();
        PhraseData data = new PhraseData();
        data.setContent("content");
        phraserequest.setData(data);
        validator.validate(phraserequest);
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowErrorExceptionIfContentMoreThan50() {
        PhraseRequest phraseRequest = new PhraseRequest();
        PhraseData data = new PhraseData();
        data.setAuthorId("123");
        data.setContent("12345678901234567890123456789012345678901234567890123");
        phraseRequest.setData(data);
        validator.validate(phraseRequest);
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowErrorExceptionGivenLatitudeIsMoreThan90() {
        PhraseRequest phraseRequest = new PhraseRequest();
        PhraseData data = new PhraseData();
        data.setContent("content");
        data.setAuthorId("123");
        data.setPoint(new Point(90, 100));
        phraseRequest.setData(data);

        validator.validate(phraseRequest);
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowErrorExceptionGivenLongitudeIsMoreThan180() {
        PhraseRequest phraseRequest = new PhraseRequest();
        PhraseData data = new PhraseData();
        data.setContent("content");
        data.setAuthorId("123");
        data.setPoint(new Point(80, 180));
        phraseRequest.setData(data);

        validator.validate(phraseRequest);
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowBadRequestGivenObjectIsNull() {
        validator.validate((Long) null);
    }
}