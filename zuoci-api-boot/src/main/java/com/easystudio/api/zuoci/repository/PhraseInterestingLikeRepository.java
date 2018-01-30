package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.InterestingLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhraseInterestingLikeRepository extends JpaRepository<InterestingLike, Long> {
    List<InterestingLike> findByPhraseId(Long phraseId);
}
