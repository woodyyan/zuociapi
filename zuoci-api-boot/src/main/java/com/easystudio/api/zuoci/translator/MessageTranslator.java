package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.Message;
import com.easystudio.api.zuoci.model.*;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageTranslator {
    public Messages toMessages(Page<Message> pagedMessages) {
        PagingMeta meta = new PagingMeta();
        meta.setPageNumber(pagedMessages.getNumber());
        meta.setPageSize(pagedMessages.getSize());
        meta.setTotalElements(pagedMessages.getTotalElements());
        meta.setTotalPages(pagedMessages.getTotalPages());

        List<MessageData> data = new ArrayList<>();
        for (Message message : pagedMessages.getContent()) {
            MessageData messageData = new MessageData();
            messageData.setObjectId(message.getObjectId());
            messageData.setSenderId(message.getSenderId());
            messageData.setCommentId(message.getCommentId());
            messageData.setReceiverId(message.getReceiverId());
            messageData.setLyricId(message.getLyricId());
            messageData.setChannel(message.getChannel());
            messageData.setContent(message.getContent());
            messageData.setText(message.getText());
            messageData.setCreatedTime(message.getCreatedTime().toDateTime());
            messageData.setLastModifiedTime(message.getLastModifiedTime().toDateTime());
            data.add(messageData);
        }

        Messages messages = new Messages();
        messages.setMeta(meta);
        messages.setData(data);

        return messages;
    }

    public Message toMessageEntity(MessageRequest messageRequest) {
        Message message = new Message();
        LocalDateTime now = LocalDateTime.now();
        message.setCreatedTime(now);
        message.setLastModifiedTime(now);
        message.setChannel(messageRequest.getData().getChannel());
        message.setCommentId(messageRequest.getData().getCommentId());
        message.setContent(messageRequest.getData().getContent());
        message.setLyricId(messageRequest.getData().getLyricId());
        message.setReceiverId(messageRequest.getData().getReceiverId());
        message.setText(messageRequest.getData().getText());
        message.setSenderId(messageRequest.getData().getSenderId());
        return message;
    }

    public MessageResponse toMessageResponse(Message message) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.getData().setObjectId(message.getObjectId());
        messageResponse.getData().setText(message.getText());
        messageResponse.getData().setContent(message.getContent());
        messageResponse.getData().setChannel(message.getChannel());
        messageResponse.getData().setLyricId(message.getLyricId());
        messageResponse.getData().setReceiverId(message.getReceiverId());
        messageResponse.getData().setCommentId(message.getCommentId());
        messageResponse.getData().setSenderId(message.getSenderId());
        messageResponse.getData().setLastModifiedTime(message.getLastModifiedTime().toDateTime());
        messageResponse.getData().setCreatedTime(message.getCreatedTime().toDateTime());

        return messageResponse;
    }
}
