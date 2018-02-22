package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.Phrase;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhraseRepository extends PagingAndSortingRepository<Phrase, Long> {

    Page<Phrase> findByIsValidAndIsVisible(boolean isValid, boolean isVisible, Pageable page);

    Page<Phrase> findByIsValidAndIsVisibleAndAuthorId(boolean isValid, boolean isVisible, String authorId, Pageable pageable);

    @Query("SELECT COUNT(*) FROM Phrase p WHERE p.content=:content AND p.createdTime > :dateTime")
    Long countByContentInToday(@Param("content") String content, @Param("dateTime") LocalDateTime dateTime);
}
