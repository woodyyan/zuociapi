package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.StarredPhrase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarredPhraseRepository extends PagingAndSortingRepository<StarredPhrase, Long> {

    Page<StarredPhrase> findByUserId(String userId, Pageable page);
}
