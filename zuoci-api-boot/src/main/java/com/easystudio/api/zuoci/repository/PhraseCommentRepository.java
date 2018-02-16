package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.PhraseComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhraseCommentRepository extends JpaRepository<PhraseComment, Long> {
    Page<PhraseComment> findByPhraseIdAndIsVisible(Long phraseId, boolean isVisible, Pageable page);
}
