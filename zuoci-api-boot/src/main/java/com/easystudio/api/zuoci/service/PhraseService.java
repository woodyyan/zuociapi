package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.DeletedPhrase;
import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.PhraseData;
import com.easystudio.api.zuoci.model.ViewCountRequest;
import com.easystudio.api.zuoci.model.error.Error;
import com.easystudio.api.zuoci.repository.DeletedPhraseRepository;
import com.easystudio.api.zuoci.repository.PhraseRepository;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import org.joda.time.LocalDateTime;
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

    public Long getPhraseCountByContentInToday(String content) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = new LocalDateTime(
                now.getYear(),
                now.getMonthOfYear(),
                now.getDayOfMonth(),
                0, 0, 0);
        return repository.countByContentInToday(content, yesterday);
    }
}
