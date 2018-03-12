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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static com.easystudio.api.zuoci.model.error.ErrorBuilder.buildNotFoundError;
import static com.google.common.base.Strings.isNullOrEmpty;

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

    public Page<Phrase> searchPhrase(Boolean isValid, Boolean isVisible, String authorId, Pageable page) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(page.getPageNumber(), page.getPageSize(), sort);
        return repository.findAll(getPhraseSpec(isValid, isVisible, authorId), pageable);
    }

    private Specification<Phrase> getPhraseSpec(Boolean isValid, Boolean isVisible, String authorId) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();
            Path<Boolean> isValidPath = root.get("isValid");
            Predicate isValidPredicate = cb.equal(isValidPath, isValid);
            predicates.add(isValidPredicate);

            if (!isNullOrEmpty(authorId)) {
                Path<Object> authorIdPath = root.get("authorId");
                Predicate authorPredicate = cb.equal(authorIdPath, authorId);
                predicates.add(authorPredicate);
            }

            if (isVisible != null) {
                Path<Boolean> visiblePath = root.get("isVisible");
                Predicate visiblePredicate = cb.equal(visiblePath, isVisible);
                predicates.add(visiblePredicate);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
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

    public Long countPhrase(String content, String authorId, boolean isVisible) {
        if (!isNullOrEmpty(authorId)) {
            return repository.countByAuthorId(authorId, isVisible);
        } else {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime yesterday = new LocalDateTime(
                    now.getYear(),
                    now.getMonthOfYear(),
                    now.getDayOfMonth(),
                    0, 0, 0);
            return repository.countByContentInToday(content, yesterday, isVisible);
        }
    }

    public Phrase getPhrase(Long objectId) {
        return repository.findOne(objectId);
    }
}
