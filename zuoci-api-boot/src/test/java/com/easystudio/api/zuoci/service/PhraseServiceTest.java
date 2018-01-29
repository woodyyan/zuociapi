package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.PhraseData;
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

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.is;

@RunWith(EasyMockRunner.class)
public class PhraseServiceTest extends EasyMockSupport {

    @TestSubject
    private PhraseService service = new PhraseService();

    @Mock
    private PhraseRepository repository;

    @Mock
    private PhraseTranslator translator;

    @Test
    public void shouldSearchPhraseGivenIsValidAndIsVisibleAndPage() throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(0, 20, sort);

        List<Phrase> content = new ArrayList<>();
        Phrase phrase = new Phrase();
        phrase.setContent("content");
        content.add(phrase);
        Page<Phrase> pagedPhrases = new PageImpl<>(content);

        expect(repository.findByIsValidAndIsVisible(true, true, pageable)).andReturn(pagedPhrases);

        replayAll();
        Page<Phrase> actual = service.searchPhrase(true, true, pageable);
        verifyAll();

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
}