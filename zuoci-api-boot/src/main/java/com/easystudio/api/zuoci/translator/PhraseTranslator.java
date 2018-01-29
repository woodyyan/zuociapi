package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.PagingMeta;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.Phrases;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        phrase.setPoint(data.getPoint());
        phrase.setLastModifiedTime(LocalDateTime.now());
        phrase.setValid(true);
        phrase.setViewCount(0L);
        phrase.setVisible(true);
        return phrase;
    }

    public ResponseEntity<Phrases> toPhraseResponse(Page<Phrase> pagedPhrases) {
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
        return new ResponseEntity<>(phrases, HttpStatus.OK);
    }

    private PhraseData toPhraseData(Phrase phrase) {
        PhraseData data = new PhraseData();
        data.setContent(phrase.getContent());
        data.setObjectId(phrase.getObjectId());
        data.setViewCount(phrase.getViewCount());
        data.setAuthorId(phrase.getAuthorId());
        data.setCreatedTime(phrase.getCreatedTime().toDateTime());
        data.setLastModifiedTime(phrase.getLastModifiedTime().toDateTime());
        data.setLocation(phrase.getLocation());
        data.setPoint(phrase.getPoint());

        return data;
    }
}
