package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.InterestingLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhraseInterestingLikeRepository extends JpaRepository<InterestingLike, Long> {

    Long countByPhraseId(Long phraseId);
}
