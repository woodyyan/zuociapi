package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.PhraseData;
import org.joda.time.LocalDateTime;

public class PhraseTranslator {
    public Phrase toPhraseEntity(PhraseData data) {
        Phrase phrase = new Phrase();
        phrase.setLocation(data.getLocation());
        phrase.setAuthorId(data.getAuthorId());
        phrase.setContent(data.getContent());
        phrase.setCreatedTime(LocalDateTime.now());
        phrase.setDeleted(false);
        phrase.setLastModifiedTime(LocalDateTime.now());
        phrase.setValid(true);
        phrase.setViewCount(0L);
        phrase.setVisible(true);
        return phrase;
    }
}
