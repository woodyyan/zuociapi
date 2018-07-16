package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.DeletedPhrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedPhraseRepository extends JpaRepository<DeletedPhrase, Long> {
}
