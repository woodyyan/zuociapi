package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.ResonanceLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhraseResonanceLikeRepository extends JpaRepository<ResonanceLike, Long> {

    Long countByPhraseId(Long phraseId);
}
