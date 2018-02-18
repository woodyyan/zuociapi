package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.ResonanceLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhraseResonanceLikeRepository extends JpaRepository<ResonanceLike, Long> {

    List<ResonanceLike> findByPhraseId(Long phraseId);

    Long countByPhraseId(Long phraseId);
}
