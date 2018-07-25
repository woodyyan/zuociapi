package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.Message;
import com.easystudio.api.zuoci.entity.Phrase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepository extends PagingAndSortingRepository<Phrase, Long> {
    Page<Message> findByReceiverId(String receiverId, Pageable page);
}
