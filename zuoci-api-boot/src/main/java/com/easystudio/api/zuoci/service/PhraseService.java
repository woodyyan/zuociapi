package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.DeletedPhrase;
import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.model.ViewCountRequest;
import com.easystudio.api.zuoci.model.error.Error;
import com.easystudio.api.zuoci.repository.DeletedPhraseRepository;
import com.easystudio.api.zuoci.repository.PhraseRepository;
import com.easystudio.api.zuoci.service.specification.PhraseCountSpecification;
import com.easystudio.api.zuoci.service.specification.PhraseSpecification;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.easystudio.api.zuoci.model.error.ErrorBuilder.buildNotFoundError;

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

    public Phrases searchPhrase(Boolean isValid, Boolean isVisible, String authorId, Pageable page) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(page.getPageNumber(), page.getPageSize(), sort);
        Page<Phrase> pagedPhrases = repository.findAll(new PhraseSpecification(isValid, isVisible, authorId), pageable);
        return translator.toPhrases(pagedPhrases);
    }

    public void deletePhrase(Long objectId) {
        Phrase phrase = repository.findOne(objectId);
        if (phrase != null) {
            repository.delete(objectId);
            DeletedPhrase deletedPhrase = translator.toDeletedPhrase(phrase);
            deleteRepository.save(deletedPhrase);
        } else {
            Error error = buildNotFoundError("Phrase not found");
            throw new ErrorException(HttpStatus.NOT_FOUND, error);
        }
    }

    public void updateViewCount(Long objectId, ViewCountRequest viewCountRequest) {
        Phrase phrase = repository.findOne(objectId);
        if (phrase != null) {
            Long viewCount = phrase.getViewCount();
            Long amount = viewCountRequest.getAmount();
            phrase.setViewCount(viewCount + amount);
            repository.save(phrase);
        } else {
            Error error = buildNotFoundError("Phrase not found");
            throw new ErrorException(HttpStatus.NOT_FOUND, error);
        }
    }

    public Long countPhrase(String content, String authorId, Boolean isVisible) {
        return repository.count(new PhraseCountSpecification(content, authorId, isVisible));
    }

    public PhraseData getPhrase(Long objectId) {
        Phrase phrase = repository.findOne(objectId);
        return translator.toPhraseData(phrase);
    }
}
