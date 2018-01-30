package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.FeelingLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhraseFeelingLikeRepository extends JpaRepository<FeelingLike, Long> {
    List<FeelingLike> findByPhraseId(Long phraseId);
}
