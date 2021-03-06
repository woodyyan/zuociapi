package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.Lyric;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LyricRepository extends PagingAndSortingRepository<Lyric, Long>, JpaSpecificationExecutor<Lyric> {
    Lyric findOneByObjectId(String objectId);
}
