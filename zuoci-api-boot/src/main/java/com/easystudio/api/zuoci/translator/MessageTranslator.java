package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.Message;
import com.easystudio.api.zuoci.model.MessageData;
import com.easystudio.api.zuoci.model.Messages;
import com.easystudio.api.zuoci.model.PagingMeta;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageTranslator {
    public Messages toMessageResponse(Page<Message> pagedMessages) {
        PagingMeta meta = new PagingMeta();
        meta.setPageNumber(pagedMessages.getNumber());
        meta.setPageSize(pagedMessages.getSize());
        meta.setTotalElements(pagedMessages.getTotalElements());
        meta.setTotalPages(pagedMessages.getTotalPages());

        List<MessageData> data = new ArrayList<>();
        for (Message message : pagedMessages.getContent()) {
            MessageData messageData = new MessageData();
            messageData.setSenderId(message.getSenderId());
            messageData.setCommentId(message.getCommentId());
            messageData.setReceiverId(message.getReceiverId());
            messageData.setLyricId(message.getLyricId());
            messageData.setChannel(message.getChannel());
            messageData.setContent(message.getContent());
            messageData.setText(message.getText());
            data.add(messageData);
        }

        Messages messages = new Messages();
        messages.setMeta(meta);
        messages.setData(data);

        return messages;
    }
}
