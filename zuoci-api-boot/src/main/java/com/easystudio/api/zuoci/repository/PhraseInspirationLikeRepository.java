package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.InspirationLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhraseInspirationLikeRepository extends JpaRepository<InspirationLike, Long> {

    Long countByPhraseId(Long phraseId);
}
