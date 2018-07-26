package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.Message;
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
        Messages messages = translator.toMessageResponse(pagedMessage);

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
    }
}