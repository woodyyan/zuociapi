package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.Message;
import com.easystudio.api.zuoci.model.MessageRequest;
import com.easystudio.api.zuoci.model.Messages;
import com.easystudio.api.zuoci.repository.MessageRepository;
import com.easystudio.api.zuoci.translator.MessageTranslator;
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

    @Autowired
    private MessageTranslator translator;

    public Messages searchMessage(String receiverId, Pageable page) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = new PageRequest(page.getPageNumber(), page.getPageSize(), sort);
        Page<Message> pagedMessages = repository.findByReceiverId(receiverId, pageable);
        return translator.toMessageResponse(pagedMessages);
    }

    public void createMessage(MessageRequest messageRequest) {
        Message message = translator.toMessageEntity(messageRequest);
        repository.save(message);
    }
}
