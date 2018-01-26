package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.model.Phrases;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class PhraseServiceTest {

    @TestSubject
    private PhraseService service = new PhraseService();

    @Test
    public void searchPhrase() throws Exception {
        int limit = 1;
        Phrases phrases = service.searchPhrase(limit);

        Assert.assertThat(phrases.getData().size(), is(1));
        Assert.assertThat(phrases.getData().get(0).getContent(), is("content"));
    }
}