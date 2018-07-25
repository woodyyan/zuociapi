package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.Message;
import com.easystudio.api.zuoci.model.Messages;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MessageTranslator {
    public ResponseEntity<Messages> toMessageResponse(Page<Message> pagedMessages) {
        return null;
    }
}
