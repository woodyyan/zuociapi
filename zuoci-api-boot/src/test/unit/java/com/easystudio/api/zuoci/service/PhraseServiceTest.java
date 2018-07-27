package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.DeletedPhrase;
import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.model.ViewCountOperation;
import com.easystudio.api.zuoci.model.ViewCountRequest;
import com.easystudio.api.zuoci.repository.DeletedPhraseRepository;
import com.easystudio.api.zuoci.repository.PhraseRepository;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.anyObject;
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
    public void shouldSearchPhraseGivenIsValidAndIsVisibleAndPageWhenUserIdIsNull() {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(0, 20, sort);

        List<Phrase> content = new ArrayList<>();
        Page<Phrase> pagedPhrases = new PageImpl<>(content);

        Phrases phrases = new Phrases();
        PhraseData data = new PhraseData();
        data.setContent("content");
        phrases.getData().add(data);
        phrases.getMeta().setTotalElements(1);
        phrases.getMeta().setTotalPages(1);
        phrases.getMeta().setPageNumber(0);
        phrases.getMeta().setPageSize(1);
        expect(repository.findAll(anyObject(), anyObject(Pageable.class))).andReturn(pagedPhrases);
        expect(translator.toPhrases(pagedPhrases)).andReturn(phrases);

        replayAll();
        Phrases actual = service.searchPhrase(true, true, null, pageable);
        verifyAll();

        Assert.assertThat(actual.getMeta().getTotalElements(), is(1L));
        Assert.assertThat(actual.getMeta().getTotalPages(), is(1L));
        Assert.assertThat(actual.getMeta().getPageNumber(), is(0L));
        Assert.assertThat(actual.getMeta().getPageSize(), is(1L));
        Assert.assertThat(actual.getData().size(), is(1));
        Assert.assertThat(actual.getData().get(0).getContent(), is("content"));
    }

    @Test
    public void shouldSearchPhraseGivenIsValidAndIsVisibleAndPageWhenUserIdIsEmpty() {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(0, 20, sort);

        List<Phrase> content = new ArrayList<>();
        Page<Phrase> pagedPhrases = new PageImpl<>(content);

        PhraseData data = new PhraseData();
        data.setContent("content");
        Phrases phrases = new Phrases();
        phrases.getMeta().setTotalElements(1);
        phrases.getMeta().setTotalPages(1);
        phrases.getMeta().setPageNumber(0);
        phrases.getMeta().setPageSize(1);
        phrases.getData().add(data);
        expect(repository.findAll(anyObject(), anyObject(Pageable.class))).andReturn(pagedPhrases);
        expect(translator.toPhrases(pagedPhrases)).andReturn(phrases);

        replayAll();
        Phrases actual = service.searchPhrase(true, true, "", pageable);
        verifyAll();

        Assert.assertThat(actual.getMeta().getTotalElements(), is(1L));
        Assert.assertThat(actual.getMeta().getTotalPages(), is(1L));
        Assert.assertThat(actual.getMeta().getPageNumber(), is(0L));
        Assert.assertThat(actual.getMeta().getPageSize(), is(1L));
        Assert.assertThat(actual.getData().size(), is(1));
        Assert.assertThat(actual.getData().get(0).getContent(), is("content"));
    }

    @Test
    public void shouldSearchPhraseGivenIsValidAndIsVisibleAndUserIdAndPage() throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(0, 20, sort);
        String authorId = "abc";

        List<Phrase> content = new ArrayList<>();
        Page<Phrase> pagedPhrases = new PageImpl<>(content);

        Phrases phrases = new Phrases();
        phrases.getMeta().setPageSize(1);
        phrases.getMeta().setPageNumber(0);
        phrases.getMeta().setTotalPages(1);
        phrases.getMeta().setTotalElements(1);
        PhraseData data = new PhraseData();
        data.setContent("content");
        phrases.getData().add(data);
        expect(repository.findAll(anyObject(), anyObject(Pageable.class))).andReturn(pagedPhrases);
        expect(translator.toPhrases(pagedPhrases)).andReturn(phrases);

        replayAll();
        Phrases actual = service.searchPhrase(true, true, authorId, pageable);
        verifyAll();

        Assert.assertThat(actual.getMeta().getTotalElements(), is(1L));
        Assert.assertThat(actual.getMeta().getTotalPages(), is(1L));
        Assert.assertThat(actual.getMeta().getPageNumber(), is(0L));
        Assert.assertThat(actual.getMeta().getPageSize(), is(1L));
        Assert.assertThat(actual.getData().size(), is(1));
        Assert.assertThat(actual.getData().get(0).getContent(), is("content"));
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

        expect(repository.count(anyObject())).andReturn(10L);

        replayAll();
        Long count = service.countPhrase(content, null, true);
        verifyAll();

        Assert.assertThat(count, is(10L));
    }

    @Test
    public void shouldGetPhraseCountGivenAuthorId() {
        String authorId = "123";
        expect(repository.count(anyObject())).andReturn(10L);

        replayAll();
        Long count = service.countPhrase(null, authorId, true);
        verifyAll();

        Assert.assertThat(count, is(10L));
    }

    @Test
    public void shouldGetPhraseGivenObjectId() {
        Long objectId = 1L;

        Phrase value = new Phrase();
        expect(repository.findOne(objectId)).andReturn(value);
        PhraseData phraseData = new PhraseData();
        phraseData.setContent("content");
        expect(translator.toPhraseData(value)).andReturn(phraseData);

        replayAll();
        PhraseData data = service.getPhrase(objectId);
        verifyAll();

        Assert.assertThat(data.getContent(), is("content"));
    }
}