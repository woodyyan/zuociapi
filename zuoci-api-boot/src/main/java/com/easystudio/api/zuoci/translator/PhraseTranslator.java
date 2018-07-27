package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.DeletedPhrase;
import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.PagingMeta;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.Phrases;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhraseTranslator {
    public Phrase toPhraseEntity(PhraseData data) {
        Phrase phrase = new Phrase();
        phrase.setLocation(data.getLocation());
        phrase.setAuthorId(data.getAuthorId());
        phrase.setContent(data.getContent());
        phrase.setCreatedTime(LocalDateTime.now());
        phrase.setLastModifiedTime(LocalDateTime.now());
        phrase.setValid(true);
        phrase.setViewCount(0L);
        phrase.setVisible(true);
        return phrase;
    }

    public Phrases toPhrases(Page<Phrase> pagedPhrases) {
        Phrases phrases = new Phrases();
        List<PhraseData> data = new ArrayList<>();
        for (Phrase phrase : pagedPhrases.getContent()) {
            PhraseData phraseData = toPhraseData(phrase);
            data.add(phraseData);
        }
        phrases.setData(data);
        PagingMeta meta = new PagingMeta();
        meta.setPageNumber(pagedPhrases.getNumber());
        meta.setPageSize(pagedPhrases.getSize());
        meta.setTotalElements(pagedPhrases.getTotalElements());
        meta.setTotalPages(pagedPhrases.getTotalPages());
        phrases.setMeta(meta);
        return phrases;
    }

    public PhraseData toPhraseData(Phrase phrase) {
        PhraseData data = new PhraseData();
        data.setContent(phrase.getContent());
        data.setObjectId(phrase.getObjectId());
        data.setViewCount(phrase.getViewCount());
        data.setAuthorId(phrase.getAuthorId());
        data.setCreatedTime(phrase.getCreatedTime().toDateTime());
        data.setLastModifiedTime(phrase.getLastModifiedTime().toDateTime());
        data.setLocation(phrase.getLocation());

        return data;
    }

    public DeletedPhrase toDeletedPhrase(Phrase phrase) {
        DeletedPhrase deletedPhrase = new DeletedPhrase();
        deletedPhrase.setAuthorId(phrase.getAuthorId());
        deletedPhrase.setContent(phrase.getContent());
        deletedPhrase.setCreatedTime(phrase.getCreatedTime());
        deletedPhrase.setLastModifiedTime(phrase.getLastModifiedTime());
        deletedPhrase.setLocation(phrase.getLocation());
        deletedPhrase.setViewCount(phrase.getViewCount());
        return deletedPhrase;
    }
}
