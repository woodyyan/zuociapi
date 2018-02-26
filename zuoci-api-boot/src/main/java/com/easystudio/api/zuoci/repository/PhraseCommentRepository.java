package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.PhraseComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhraseCommentRepository extends JpaRepository<PhraseComment, Long> {
    Page<PhraseComment> findByPhraseIdAndIsVisible(Long phraseId, boolean isVisible, Pageable page);

    Page<PhraseComment> findAllByRepliedUserIdOrPhraseAuthorId(String repliedUserId, String phraseAuthorId, Pageable page);

    @Query("SELECT COUNT(*) FROM PhraseComment p WHERE p.phraseId=:phraseId AND p.isVisible=:isVisible")
    Long countByPhraseId(@Param("phraseId") Long phraseId, @Param("isVisible") boolean isVisible);

    @Query("SELECT COUNT(*) FROM PhraseComment p WHERE (p.phraseAuthorId=:userId OR p.repliedUserId=:userId) AND p.isVisible=:isVisible")
    Long countByUserId(@Param("userId") String userId, @Param("isVisible") boolean isVisible);
}
