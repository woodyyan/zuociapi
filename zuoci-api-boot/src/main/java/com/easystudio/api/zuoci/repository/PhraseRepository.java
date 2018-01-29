package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.Phrase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhraseRepository extends PagingAndSortingRepository<Phrase, Long> {

    Page<Phrase> findByIsValidAndIsVisible(boolean isValid, boolean isVisible, Pageable page);
}
