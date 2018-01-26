package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.Phrase;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhraseRepository extends PagingAndSortingRepository<Phrase, Long> {
}
