package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.InspirationLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhraseInspirationLikeRepository extends JpaRepository<InspirationLike, Long> {
    List<InspirationLike> findByPhraseId(Long phraseId);

    Long countByPhraseId(Long phraseId);
}
