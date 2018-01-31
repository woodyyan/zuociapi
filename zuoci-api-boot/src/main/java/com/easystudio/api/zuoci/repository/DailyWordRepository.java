package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.DailyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyWordRepository extends JpaRepository<DailyWord, Long> {
    DailyWord findFirstByOrderByCreatedTimeDesc();
}
