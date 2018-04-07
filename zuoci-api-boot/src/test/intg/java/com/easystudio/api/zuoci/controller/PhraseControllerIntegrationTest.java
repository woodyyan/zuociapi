package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.repository.PhraseRepository;
import com.easystudio.api.zuoci.service.specification.PhraseSpecification;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class PhraseControllerIntegrationTest extends AbstractControllerIntegrationTest {
    private static final String CONTENT = "content";
    private static final String LOCATION = "Beijing";
    private static final String AUTHOR_ID = "123";
    private static final long OBJECT_ID = 101L;

    @MockBean
    private PhraseRepository phraseRepository;

    @Test
    public void shouldGetPhrases() throws Exception {
        List<Phrase> content = new ArrayList<>();
        Phrase phrase = new Phrase();
        phrase.setObjectId(OBJECT_ID);
        phrase.setLastModifiedTime(LocalDateTime.now());
        phrase.setCreatedTime(LocalDateTime.now());
        phrase.setViewCount(1L);
        phrase.setLocation(LOCATION);
        phrase.setAuthorId(AUTHOR_ID);
        phrase.setVisible(true);
        phrase.setValid(true);
        phrase.setContent(CONTENT);
        content.add(phrase);
        Pageable page = new PageRequest(0, 20);
        Page<Phrase> phrasePage = new PageImpl<>(content, page, 1);
        when(phraseRepository.findAll(Mockito.any(PhraseSpecification.class), Mockito.any(Pageable.class)))
                .thenReturn(phrasePage);

        MockHttpServletResponse response = performGetRequest("/phrase?isValid=true&authorId=123&page=0&size=20");

        Assert.assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        Phrases phrases = objectMapper.readValue(response.getContentAsString(), Phrases.class);
        Assert.assertThat(phrases.getData().size(), is(1));
        Assert.assertThat(phrases.getData().get(0).getObjectId(), is(OBJECT_ID));
        Assert.assertThat(phrases.getData().get(0).getContent(), is(CONTENT));
        Assert.assertThat(phrases.getData().get(0).getAuthorId(), is(AUTHOR_ID));
        Assert.assertThat(phrases.getData().get(0).getLocation(), is(LOCATION));
        Assert.assertThat(phrases.getData().get(0).getViewCount(), is(1L));
        Assert.assertNotNull(phrases.getData().get(0).getCreatedTime());
        Assert.assertNotNull(phrases.getData().get(0).getLastModifiedTime());
        Assert.assertThat(phrases.getMeta().getPageNumber(), is(0L));
        Assert.assertThat(phrases.getMeta().getPageSize(), is(20L));
        Assert.assertThat(phrases.getMeta().getTotalElements(), is(1L));
        Assert.assertThat(phrases.getMeta().getTotalPages(), is(1L));
    }
}
