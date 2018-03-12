package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.Phrase;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhraseRepository extends PagingAndSortingRepository<Phrase, Long>, JpaSpecificationExecutor<Phrase> {
    
    @Query("SELECT COUNT(*) FROM Phrase p WHERE p.content=:content AND p.isVisible=:isVisible AND p.createdTime > :dateTime")
    Long countByContentInToday(@Param("content") String content, @Param("dateTime") LocalDateTime dateTime, @Param("isVisible") boolean isVisible);

    @Query("SELECT COUNT(*) FROM Phrase p WHERE p.authorId=:authorId AND p.isVisible=:isVisible AND p.isValid=true")
    Long countByAuthorId(@Param("authorId") String authorId, @Param("isVisible") boolean isVisible);
}
