package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.NormalLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhraseNormalLikeRepository extends JpaRepository<NormalLike, Long> {

    List<NormalLike> findByPhraseId(Long phraseId);

    Long countByPhraseId(Long phraseId);
}
