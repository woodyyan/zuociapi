package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.DeletedPhrase;
import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.ViewCountOperation;
import com.easystudio.api.zuoci.model.ViewCountRequest;
import com.easystudio.api.zuoci.repository.DeletedPhraseRepository;
import com.easystudio.api.zuoci.repository.PhraseRepository;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.is;

@RunWith(EasyMockRunner.class)
public class PhraseServiceTest extends EasyMockSupport {

    @TestSubject
    private PhraseService service = new PhraseService();

    @Mock
    private PhraseRepository repository;

    @Mock
    private DeletedPhraseRepository deleteRepository;

    @Mock
    private PhraseTranslator translator;

    @Test
    public void shouldSearchPhraseGivenIsValidAndIsVisibleAndPageWhenUserIdIsNull() throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(0, 20, sort);

        List<Phrase> content = new ArrayList<>();
        Phrase phrase = new Phrase();
        phrase.setContent("content");
        content.add(phrase);
        Page<Phrase> pagedPhrases = new PageImpl<>(content);

        expect(repository.findByIsValidAndIsVisible(true, true, pageable)).andReturn(pagedPhrases);

        replayAll();
        Page<Phrase> actual = service.searchPhrase(true, true, null, pageable);
        verifyAll();

        Assert.assertThat(actual.getTotalElements(), is(1L));
        Assert.assertThat(actual.getTotalPages(), is(1));
        Assert.assertThat(actual.getNumber(), is(0));
        Assert.assertThat(actual.getNumberOfElements(), is(1));
        Assert.assertThat(actual.getContent().size(), is(1));
        Assert.assertThat(actual.getContent().get(0).getContent(), is("content"));
    }

    @Test
    public void shouldSearchPhraseGivenIsValidAndIsVisibleAndPageWhenUserIdIsEmpty() throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(0, 20, sort);

        List<Phrase> content = new ArrayList<>();
        Phrase phrase = new Phrase();
        phrase.setContent("content");
        content.add(phrase);
        Page<Phrase> pagedPhrases = new PageImpl<>(content);

        expect(repository.findByIsValidAndIsVisible(true, true, pageable)).andReturn(pagedPhrases);

        replayAll();
        Page<Phrase> actual = service.searchPhrase(true, true, "", pageable);
        verifyAll();

        Assert.assertThat(actual.getTotalElements(), is(1L));
        Assert.assertThat(actual.getTotalPages(), is(1));
        Assert.assertThat(actual.getNumber(), is(0));
        Assert.assertThat(actual.getNumberOfElements(), is(1));
        Assert.assertThat(actual.getContent().size(), is(1));
        Assert.assertThat(actual.getContent().get(0).getContent(), is("content"));
    }

    @Test
    public void shouldSearchPhraseGivenIsValidAndIsVisibleAndUserIdAndPage() throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(0, 20, sort);
        String authorId = "abc";

        List<Phrase> content = new ArrayList<>();
        Phrase phrase = new Phrase();
        phrase.setContent("content");
        content.add(phrase);
        Page<Phrase> pagedPhrases = new PageImpl<>(content);

        expect(repository.findByIsValidAndIsVisibleAndAuthorId(true, true, authorId, pageable))
                .andReturn(pagedPhrases);

        replayAll();
        Page<Phrase> actual = service.searchPhrase(true, true, authorId, pageable);
        verifyAll();

        Assert.assertThat(actual.getTotalElements(), is(1L));
        Assert.assertThat(actual.getTotalPages(), is(1));
        Assert.assertThat(actual.getNumber(), is(0));
        Assert.assertThat(actual.getNumberOfElements(), is(1));
        Assert.assertThat(actual.getContent().size(), is(1));
        Assert.assertThat(actual.getContent().get(0).getContent(), is("content"));
    }

    @Test
    public void shouldCreatePhraseGivenPhraseData() {
        PhraseData data = new PhraseData();
        data.setAuthorId("123");
        data.setContent("content");

        Phrase phrase = new Phrase();
        expect(translator.toPhraseEntity(data)).andReturn(phrase);
        expect(repository.save(phrase)).andReturn(phrase);

        replayAll();
        service.createPhrase(data);
        verifyAll();
    }

    @Test
    public void shouldDeletePhraseGivenPhraseId() {
        Long objectId = 123L;
        Phrase phrase = new Phrase();
        DeletedPhrase deletedPhrase = new DeletedPhrase();

        expect(repository.findOne(objectId)).andReturn(phrase);
        repository.delete(objectId);
        expect(translator.toDeletedPhrase(phrase)).andReturn(deletedPhrase);
        expect(deleteRepository.save(deletedPhrase)).andReturn(deletedPhrase);

        replayAll();
        service.deletePhrase(objectId);
        verifyAll();
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowExceptionWhenPhraseIdIsNotExist() {
        Long objectId = 123L;

        expect(repository.findOne(objectId)).andReturn(null);

        replayAll();
        service.deletePhrase(objectId);
        verifyAll();
    }

    @Test(expected = ErrorException.class)
    public void shouldThrowErrorExceptionGivenObjectIdIsNotExits() {
        ViewCountRequest request = new ViewCountRequest();
        Long objectId = 123L;

        expect(repository.findOne(objectId)).andReturn(null);

        replayAll();
        service.updateViewCount(objectId, request);
        verifyAll();
    }

    @Test
    public void shouldIncrementViewCountGivenObjectIdAndViewCount() {
        ViewCountRequest request = new ViewCountRequest();
        request.setAmount(10L);
        request.setOperation(ViewCountOperation.Increment);
        Long objectId = 123L;
        Phrase phrase = new Phrase();
        phrase.setViewCount(0L);

        expect(repository.findOne(objectId)).andReturn(phrase);
        expect(repository.save(phrase)).andReturn(phrase);

        replayAll();
        service.updateViewCount(objectId, request);
        verifyAll();

        Assert.assertThat(phrase.getViewCount(), is(10L));
    }

    @Test
    public void shouldGetPhraseCountGivenContentInToday() {
        String content = "content";
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateTime = new LocalDateTime(
                now.getYear(),
                now.getMonthOfYear(),
                now.getDayOfMonth(),
                0, 0, 0);

        expect(repository.countByContentInToday(content, dateTime)).andReturn(10L);

        replayAll();
        Long count = service.countPhrase(content, null);
        verifyAll();

        Assert.assertThat(count, is(10L));
    }

    @Test
    public void shouldGetPhraseCountGivenAuthorId() {
        String authorId = "123";
        expect(repository.countByAuthorId(authorId)).andReturn(10L);

        replayAll();
        Long count = service.countPhrase(null, authorId);
        verifyAll();

        Assert.assertThat(count, is(10L));
    }

    @Test
    public void shouldGetPhraseGivenObjectId() {
        Long objectId = 1L;

        Phrase value = new Phrase();
        value.setContent("content");
        expect(repository.findOne(objectId)).andReturn(value);

        replayAll();
        Phrase phrase = service.getPhrase(objectId);
        verifyAll();

        Assert.assertThat(phrase.getContent(), is("content"));
    }
}