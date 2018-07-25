package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.Message;
import com.easystudio.api.zuoci.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    public Page<Message> searchMessage(String receiverId, Pageable page) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(page.getPageNumber(), page.getPageSize(), sort);
        return repository.findByReceiverId(receiverId, pageable);
    }
}
