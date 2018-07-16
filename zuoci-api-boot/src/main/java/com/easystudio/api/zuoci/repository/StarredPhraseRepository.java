package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.StarredPhrase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StarredPhraseRepository extends PagingAndSortingRepository<StarredPhrase, Long> {

    Page<StarredPhrase> findByUserId(String userId, Pageable page);

    StarredPhrase findOneByUserIdAndPhraseId(String userId, Long phraseId);

    @Query("SELECT COUNT(*) FROM StarredPhrase p WHERE p.userId=:userId")
    Long countByUserId(@Param("userId") String userId);

    @Query("SELECT COUNT(*) FROM StarredPhrase p WHERE p.userId=:userId AND p.phraseId=:phraseId")
    Long countByUserIdAndPhraseId(@Param("userId") String userId, @Param("phraseId") Long phraseId);
}
