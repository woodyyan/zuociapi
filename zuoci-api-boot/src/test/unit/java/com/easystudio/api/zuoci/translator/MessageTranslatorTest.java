package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.Message;
import com.easystudio.api.zuoci.model.MessageData;
import com.easystudio.api.zuoci.model.MessageRequest;
import com.easystudio.api.zuoci.model.Messages;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class MessageTranslatorTest {
    private MessageTranslator translator = new MessageTranslator();

    @Test
    public void shouldReturnMessagesGivenPagedMessage() {
        Pageable page = new PageRequest(0, 1);
        List<Message> content = new ArrayList<>();
        Message message = new Message();
        message.setSenderId("sender");
        message.setText("text");
        message.setReceiverId("receiver");
        message.setLyricId("lyric");
        message.setContent("content");
        message.setCommentId("comment");
        message.setChannel("channel");
        message.setLastModifiedTime(new LocalDateTime());
        message.setCreatedTime(new LocalDateTime());
        message.setObjectId(123L);
        content.add(message);
        Page<Message> pagedMessage = new PageImpl<>(content, page, 1);
        Messages messages = translator.toMessages(pagedMessage);

        Assert.assertEquals(0, messages.getMeta().getPageNumber());
        Assert.assertEquals(1L, messages.getMeta().getTotalElements());
        Assert.assertEquals(1L, messages.getMeta().getPageSize());
        Assert.assertEquals(1L, messages.getMeta().getTotalPages());
        Assert.assertEquals(1, messages.getData().size());
        Assert.assertEquals("channel", messages.getData().get(0).getChannel());
        Assert.assertEquals("comment", messages.getData().get(0).getCommentId());
        Assert.assertEquals("content", messages.getData().get(0).getContent());
        Assert.assertEquals("lyric", messages.getData().get(0).getLyricId());
        Assert.assertEquals("receiver", messages.getData().get(0).getReceiverId());
        Assert.assertEquals("sender", messages.getData().get(0).getSenderId());
        Assert.assertEquals("text", messages.getData().get(0).getText());
        Assert.assertTrue(123L == messages.getData().get(0).getObjectId());
        Assert.assertNotNull(messages.getData().get(0).getCreatedTime());
        Assert.assertNotNull(messages.getData().get(0).getLastModifiedTime());
    }

    @Test
    public void shouldTranslateMessageReuqestToMessageEntity() {
        MessageRequest request = new MessageRequest();
        MessageData data = new MessageData();
        data.setText("text");
        data.setContent("content");
        data.setChannel("channel");
        data.setLyricId("lyric");
        data.setReceiverId("receiver");
        data.setCommentId("comment");
        data.setSenderId("sender");
        request.setData(data);

        Message message = translator.toMessageEntity(request);

        Assert.assertEquals("channel", message.getChannel());
        Assert.assertEquals("text", message.getText());
        Assert.assertEquals("sender", message.getSenderId());
        Assert.assertEquals("receiver", message.getReceiverId());
        Assert.assertEquals("lyric", message.getLyricId());
        Assert.assertEquals("content", message.getContent());
        Assert.assertEquals("comment", message.getCommentId());
        Assert.assertNotNull(message.getCreatedTime());
        Assert.assertNotNull(message.getLastModifiedTime());
    }
}