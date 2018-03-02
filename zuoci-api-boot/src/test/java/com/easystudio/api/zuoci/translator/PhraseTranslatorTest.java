package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.DeletedPhrase;
import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.Phrases;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class PhraseTranslatorTest {

    private PhraseTranslator translator = new PhraseTranslator();

    @Test
    public void shouldTranslateToPhraseEntityGivenPhraseData() throws Exception {
        String authorId = "123";
        String location = "chengdu";
        String content = "content";

        PhraseData data = new PhraseData();
        data.setAuthorId(authorId);
        data.setLocation(location);
        data.setContent(content);

        Phrase phrase = translator.toPhraseEntity(data);

        Assert.assertThat(phrase.getAuthorId(), is(authorId));
        Assert.assertThat(phrase.getContent(), is(content));
        Assert.assertThat(phrase.getLocation(), is(location));
        Assert.assertThat(phrase.getViewCount(), is(0L));
        Assert.assertThat(phrase.getCreatedTime(), lessThanOrEqualTo(LocalDateTime.now()));
        Assert.assertThat(phrase.getLastModifiedTime(), lessThanOrEqualTo(LocalDateTime.now()));
    }

    @Test
    public void shouldTranslateToPhraseResponseGivenPhraseEntity() {
        List<Phrase> content = new ArrayList<>();
        Phrase phrase = new Phrase();
        phrase.setContent("content");
        phrase.setLocation("chengdu");
        phrase.setAuthorId("123");
        phrase.setViewCount(100L);
        phrase.setObjectId(111L);
        phrase.setCreatedTime(LocalDateTime.now());
        phrase.setLastModifiedTime(LocalDateTime.now());
        content.add(phrase);
        Pageable page = new PageRequest(0, 20);
        Page<Phrase> pagedPhrases = new PageImpl<>(content, page, 1);
        ResponseEntity<Phrases> actual = translator.toPhraseResponse(pagedPhrases);

        Assert.assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(actual.getBody().getData().size(), is(1));
        Assert.assertThat(actual.getBody().getData().get(0).getContent(), is("content"));
        Assert.assertThat(actual.getBody().getData().get(0).getAuthorId(), is("123"));
        Assert.assertThat(actual.getBody().getData().get(0).getLocation(), is("chengdu"));
        Assert.assertThat(actual.getBody().getData().get(0).getViewCount(), is(100L));
        Assert.assertThat(actual.getBody().getData().get(0).getObjectId(), is(111L));
        Assert.assertThat(actual.getBody().getMeta().getPageNumber(), is(0L));
        Assert.assertThat(actual.getBody().getMeta().getPageSize(), is(20L));
        Assert.assertThat(actual.getBody().getMeta().getTotalElements(), is(1L));
        Assert.assertThat(actual.getBody().getMeta().getTotalPages(), is(1L));
    }

    @Test
    public void shouldTranslateToDeletedPhraseGivenPhrase() {
        Phrase phrase = new Phrase();
        phrase.setObjectId(123L);
        phrase.setVisible(true);
        phrase.setViewCount(100L);
        phrase.setAuthorId("111");
        phrase.setLocation("chengdu");
        phrase.setLastModifiedTime(LocalDateTime.now());
        phrase.setCreatedTime(LocalDateTime.now());
        phrase.setContent("content");

        DeletedPhrase deletedPhrase = translator.toDeletedPhrase(phrase);

        Assert.assertNull(deletedPhrase.getObjectId());
        Assert.assertThat(deletedPhrase.getAuthorId(), is("111"));
        Assert.assertThat(deletedPhrase.getContent(), is("content"));
        Assert.assertThat(deletedPhrase.getLocation(), is("chengdu"));
        Assert.assertThat(deletedPhrase.getViewCount(), is(100L));
        Assert.assertThat(deletedPhrase.getCreatedTime(), lessThanOrEqualTo(LocalDateTime.now()));
        Assert.assertThat(deletedPhrase.getLastModifiedTime(), lessThanOrEqualTo(LocalDateTime.now()));
    }

    @Test
    public void shouldTranslateToPhraseDataGivenPhrase() {
        Phrase phrase = new Phrase();
        phrase.setObjectId(1L);
        phrase.setViewCount(2L);
        phrase.setContent("content");
        phrase.setCreatedTime(LocalDateTime.now());
        phrase.setLocation("location");
        phrase.setAuthorId("abc");
        phrase.setVisible(true);
        phrase.setValid(true);
        phrase.setLastModifiedTime(LocalDateTime.now());

        PhraseData phraseData = translator.toPhraseData(phrase);

        Assert.assertThat(phraseData.getObjectId(), is(1L));
        Assert.assertThat(phraseData.getViewCount(), is(2L));
        Assert.assertThat(phraseData.getLocation(), is("location"));
        Assert.assertThat(phraseData.getAuthorId(), is("abc"));
        Assert.assertThat(phraseData.getContent(), is("content"));
        Assert.assertThat(phraseData.getCreatedTime(), lessThanOrEqualTo(DateTime.now()));
        Assert.assertThat(phraseData.getLastModifiedTime(), lessThanOrEqualTo(DateTime.now()));
    }
}