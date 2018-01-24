package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhraseRepository extends JpaRepository<Phrase, Long>{
}
