package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.Phrases;
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
import static org.hamcrest.Matchers.lessThan;
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
        phrase.setCreatedTime(LocalDateTime.now());
        phrase.setLastModifiedTime(LocalDateTime.now());
        content.add(phrase);
        Pageable page = new PageRequest(0, 20);
        Page<Phrase> pagedPhrases = new PageImpl<>(content, page, 1);
        ResponseEntity<Phrases> actual = translator.toPhraseResponse(pagedPhrases);

        Assert.assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(actual.getBody().getData().size(), is(1));
        Assert.assertThat(actual.getBody().getData().get(0).getContent(), is("content"));
        Assert.assertThat(actual.getBody().getMeta().getPageNumber(), is(0L));
        Assert.assertThat(actual.getBody().getMeta().getPageSize(), is(20L));
        Assert.assertThat(actual.getBody().getMeta().getTotalElements(), is(1L));
        Assert.assertThat(actual.getBody().getMeta().getTotalPages(), is(1L));
    }
}