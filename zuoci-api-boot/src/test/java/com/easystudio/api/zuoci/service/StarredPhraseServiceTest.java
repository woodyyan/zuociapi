package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.entity.StarredPhrase;
import com.easystudio.api.zuoci.repository.PhraseRepository;
import com.easystudio.api.zuoci.repository.StarredPhraseRepository;
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

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenPhraseIdIsNotExist() throws NotFoundException {
        Long phraseId = 1L;
        String userId = "abc";

        expect(phraseRepository.findOne(phraseId)).andReturn(null);

        replayAll();
        service.addStar(phraseId, userId);
        verifyAll();
    }

    @Test
    public void shouldSearchStarGivenUserId() {
        String userId = "abc";
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(0, 20, sort);

        List<StarredPhrase> content = new ArrayList<>();
        StarredPhrase star = new StarredPhrase();
        star.setUserId(userId);
        star.setPhrase(new Phrase());
        content.add(star);
        Page<StarredPhrase> pagedStars = new PageImpl<>(content, pageable, 1);
        expect(repository.findByUserId(userId, pageable)).andReturn(pagedStars);

        replayAll();
        Page<Phrase> phrases = service.searchStar(userId, pageable);
        verifyAll();

        Assert.assertThat(phrases.getTotalPages(), is(1));
        Assert.assertThat(phrases.getTotalElements(), is(1L));
        Assert.assertThat(phrases.getSize(), is(20));
        Assert.assertThat(phrases.getNumber(), is(0));
        Assert.assertThat(phrases.getContent().size(), is(1));
    }
}