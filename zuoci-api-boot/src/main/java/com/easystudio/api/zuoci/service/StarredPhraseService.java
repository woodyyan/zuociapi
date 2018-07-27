package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.entity.StarredPhrase;
import com.easystudio.api.zuoci.model.PhraseStarRequest;
import com.easystudio.api.zuoci.model.Phrases;
import com.easystudio.api.zuoci.repository.PhraseRepository;
import com.easystudio.api.zuoci.repository.StarredPhraseRepository;
import com.easystudio.api.zuoci.translator.PhraseTranslator;
import javassist.NotFoundException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StarredPhraseService {

    @Autowired
    private StarredPhraseRepository repository;

    @Autowired
    private PhraseRepository phraseRepository;

    @Autowired
    private PhraseTranslator translator;

    public void addStar(PhraseStarRequest request) throws NotFoundException {
        Phrase phrase = phraseRepository.findOne(request.getPhraseId());
        if (phrase != null) {
            StarredPhrase starredPhrase = new StarredPhrase();
            starredPhrase.setCreatedTime(LocalDateTime.now());
            starredPhrase.setLastModifiedTime(LocalDateTime.now());
            starredPhrase.setUserId(request.getUserId());
            starredPhrase.setPhraseId(phrase.getObjectId());
            repository.save(starredPhrase);
        } else {
            throw new NotFoundException("Phrase Id is not found.");
        }
    }

    public Phrases searchStar(String userId, Pageable page) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(page.getPageNumber(), page.getPageSize(), sort);
        Page<StarredPhrase> pagedStars = repository.findByUserId(userId, pageable);
        Stream<Phrase> phraseStream = pagedStars.getContent().stream().map(StarredPhrase::getPhrase);
        PageImpl<Phrase> pagedPhrases = new PageImpl<>(phraseStream.collect(Collectors.toList()), pageable, pagedStars.getTotalElements());
        return translator.toPhrases(pagedPhrases);
    }

    public void deleteStar(String userId, Long phraseId) {
        StarredPhrase star = repository.findOneByUserIdAndPhraseId(userId, phraseId);
        if (star != null) {
            repository.delete(star);
        }
    }

    public Long countStar(String userId, Long phraseId) {
        if (phraseId == null || phraseId == 0) {
            return repository.countByUserId(userId);
        } else {
            return repository.countByUserIdAndPhraseId(userId, phraseId);
        }
    }
}
