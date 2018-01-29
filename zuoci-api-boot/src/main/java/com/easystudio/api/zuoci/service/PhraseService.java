package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.DeletedPhrase;
import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.error.Error;
import com.easystudio.api.zuoci.repository.DeletedPhraseRepository;
import com.easystudio.api.zuoci.repository.PhraseRepository;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PhraseService {

    @Autowired
    private PhraseRepository repository;

    @Autowired
    private DeletedPhraseRepository deleteRepository;

    @Autowired
    private PhraseTranslator translator;


    public void createPhrase(PhraseData data) {
        Phrase phrase = translator.toPhraseEntity(data);
        repository.save(phrase);
    }

    public Page<Phrase> searchPhrase(boolean isValid, boolean isVisible, Pageable page) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(page.getPageNumber(), page.getPageSize(), sort);
        return repository.findByIsValidAndIsVisible(isValid, isVisible, pageable);
    }

    public void deletePhrase(Long objectId) {
        Phrase phrase = repository.findOne(objectId);
        if (phrase != null) {
            repository.delete(objectId);
            DeletedPhrase deletedPhrase = translator.toDeletedPhrase(phrase);
            deleteRepository.save(deletedPhrase);
        } else {
            buildNotFoundError();
        }
    }

    private void buildNotFoundError() {
        Error error = new Error();
        error.setStatus(HttpStatus.NOT_FOUND.name());
        error.setTitle("Phrase not found");
        error.setDetails("Phrase not found");
        throw new ErrorException(HttpStatus.NOT_FOUND, error);
    }
}
