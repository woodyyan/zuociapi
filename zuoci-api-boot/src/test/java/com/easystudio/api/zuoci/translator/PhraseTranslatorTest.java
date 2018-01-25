package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.PhraseData;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThan;

public class PhraseTranslatorTest {

    private PhraseTranslator translator = new PhraseTranslator();

    @Test
    public void toPhraseEntity() throws Exception {
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
        Assert.assertThat(phrase.getCreatedTime(), lessThan(LocalDateTime.now()));
        Assert.assertThat(phrase.getLastModifiedTime(), lessThan(LocalDateTime.now()));
    }

}