package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.PhraseRequest;
import com.easystudio.api.zuoci.repository.PhraseRepository;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PhraseService {

    @Autowired
    private PhraseRepository repository;

    public ResponseEntity<PhraseRequest> searchPhrase() {
        return null;
    }

    public void createPhrase(PhraseData data) {
        PhraseTranslator translator = new PhraseTranslator();
        Phrase phrase = translator.toPhraseEntity(data);
        repository.save(phrase);
    }
}
