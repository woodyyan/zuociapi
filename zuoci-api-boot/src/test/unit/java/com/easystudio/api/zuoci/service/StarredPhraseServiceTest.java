package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.entity.StarredPhrase;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.PhraseStarRequest;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.repository.PhraseRepository;
import com.easystudio.api.zuoci.repository.StarredPhraseRepository;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import javassist.NotFoundException;
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
import static org.hamcrest.Matchers.is;

@RunWith(EasyMockRunner.class)
public class StarredPhraseServiceTest extends EasyMockSupport {

    @TestSubject
    private StarredPhraseService service = new StarredPhraseService();

    @Mock
    private StarredPhraseRepository repository;

    @Mock
    private PhraseRepository phraseRepository;

    @Mock
    private PhraseTranslator translator;

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenPhraseIdIsNotExist() throws NotFoundException {
        Long phraseId = 1L;
        String userId = "abc";
        PhraseStarRequest request = new PhraseStarRequest();
        request.setPhraseId(phraseId);
        request.setUserId(userId);

        expect(phraseRepository.findOne(phraseId)).andReturn(null);

        replayAll();
        service.addStar(request);
        verifyAll();
    }

    @Test
    public void shouldAddStarWhenPhraseExists() throws NotFoundException {
        Long phraseId = 1L;
        String userId = "abc";
        PhraseStarRequest request = new PhraseStarRequest();
        request.setPhraseId(phraseId);
        request.setUserId(userId);

        Phrase phrase = new Phrase();
        phrase.setObjectId(2L);
        expect(phraseRepository.findOne(phraseId)).andReturn(phrase);
        StarredPhrase value = new StarredPhrase();
        expect(repository.save((StarredPhrase) anyObject())).andReturn(value);

        replayAll();
        service.addStar(request);
        verifyAll();

        Assert.assertNotNull(value);
    }

    @Test
    public void shouldSearchStarGivenUserId() {
        String userId = "abc";
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(0, 20, sort);

        List<StarredPhrase> content = new ArrayList<>();
        Page<StarredPhrase> pagedStars = new PageImpl<>(content, pageable, 1);
        Phrases expectedPhrases = new Phrases();
        expectedPhrases.getMeta().setTotalElements(1);
        expectedPhrases.getMeta().setTotalPages(1);
        expectedPhrases.getMeta().setPageNumber(0);
        expectedPhrases.getMeta().setPageSize(20);
        PhraseData data = new PhraseData();
        expectedPhrases.getData().add(data);
        expect(repository.findByUserId(userId, pageable)).andReturn(pagedStars);
        expect(translator.toPhrases(anyObject())).andReturn(expectedPhrases);

        replayAll();
        Phrases phrases = service.searchStar(userId, pageable);
        verifyAll();

        Assert.assertThat(phrases.getMeta().getTotalPages(), is(1L));
        Assert.assertThat(phrases.getMeta().getTotalElements(), is(1L));
        Assert.assertThat(phrases.getMeta().getPageSize(), is(20L));
        Assert.assertThat(phrases.getMeta().getPageNumber(), is(0L));
        Assert.assertThat(phrases.getData().size(), is(1));
    }

    @Test
    public void shouldCountStarGivenUserIdAndPhraseId() {
        String userId = "abc";
        Long phraseId = 1L;

        expect(repository.countByUserIdAndPhraseId(userId, phraseId)).andReturn(1L);

        replayAll();
        Long count = service.countStar(userId, phraseId);
        verifyAll();

        Assert.assertThat(count, is(1L));
    }

    @Test
    public void shouldCountStarGivenUserIdAndPhraseIdIsZero() {
        String userId = "abc";
        Long phraseId = 0L;

        expect(repository.countByUserId(userId)).andReturn(1L);

        replayAll();
        Long count = service.countStar(userId, phraseId);
        verifyAll();

        Assert.assertThat(count, is(1L));
    }

    @Test
    public void shouldCountStarGivenOnlyUserId() {
        String userId = "abc";

        expect(repository.countByUserId(userId)).andReturn(1L);

        replayAll();
        Long count = service.countStar(userId, null);
        verifyAll();

        Assert.assertThat(count, is(1L));
    }

    @Test
    public void shouldDeleteStarGivenUserIdAndPhraseId() {
        String userId = "123";
        Long phraseId = 1L;

        StarredPhrase star = new StarredPhrase();
        expect(repository.findOneByUserIdAndPhraseId(userId, phraseId)).andReturn(star);
        repository.delete(star);

        replayAll();
        service.deleteStar(userId, phraseId);
        verifyAll();
    }

    @Test
    public void shouldNotDeleteStarGivenUserIdAndPhraseId() {
        String userId = "123";
        Long phraseId = 1L;

        expect(repository.findOneByUserIdAndPhraseId(userId, phraseId)).andReturn(null);

        replayAll();
        service.deleteStar(userId, phraseId);
        verifyAll();
    }
}