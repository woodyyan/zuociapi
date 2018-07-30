package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Message;
import com.easystudio.api.zuoci.model.MessageData;
import com.easystudio.api.zuoci.model.MessageRequest;
import com.easystudio.api.zuoci.model.MessageResponse;
import com.easystudio.api.zuoci.model.Messages;
import com.easystudio.api.zuoci.repository.MessageRepository;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

public class MessageControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private MessageRepository messageRepository;

    @Before
    public void setUp() {
        messageRepository.deleteAll();
    }

    @Test
    public void shouldGetMessagesGivenReceiverIdAndPage() throws Exception {
        String url = "/message?receiverId=123";

        String receiverId = "123";
        Message message = new Message();
        message.setSenderId("sender");
        message.setText("text");
        message.setReceiverId(receiverId);
        message.setLyricId("lyric");
        message.setContent("content");
        message.setCommentId("comment");
        message.setChannel("channel");
        message.setCreatedTime(LocalDateTime.now());
        message.setLastModifiedTime(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        MockHttpServletResponse response = performGetRequest(url);

        Assert.assertEquals(200, response.getStatus());
        Messages messages = objectMapper.readValue(response.getContentAsString(), Messages.class);
        Assert.assertEquals(0, messages.getMeta().getPageNumber());
        Assert.assertEquals(1L, messages.getMeta().getTotalPages());
        Assert.assertEquals(20L, messages.getMeta().getPageSize());
        Assert.assertEquals(1L, messages.getMeta().getTotalElements());
        Assert.assertEquals(1, messages.getData().size());
        Assert.assertEquals(savedMessage.getObjectId(), messages.getData().get(0).getObjectId());
        Assert.assertEquals("channel", messages.getData().get(0).getChannel());
        Assert.assertEquals("text", messages.getData().get(0).getText());
        Assert.assertEquals("sender", messages.getData().get(0).getSenderId());
        Assert.assertEquals("123", messages.getData().get(0).getReceiverId());
        Assert.assertEquals("lyric", messages.getData().get(0).getLyricId());
        Assert.assertEquals("content", messages.getData().get(0).getContent());
        Assert.assertEquals("comment", messages.getData().get(0).getCommentId());
        Assert.assertNotNull(messages.getData().get(0).getCreatedTime());
        Assert.assertNotNull(messages.getData().get(0).getLastModifiedTime());
    }

    @Test
    public void shouldCreateMessageSuccessfully() throws Exception {
        MessageRequest messageRequest = new MessageRequest();
        MessageData data = new MessageData();
        data.setSenderId("sender");
        data.setCommentId("comment");
        data.setReceiverId("receiver");
        data.setObjectId(1L);
        data.setLyricId("lyric");
        data.setChannel("channel");
        data.setContent("content");
        data.setText("text");
        messageRequest.setData(data);
        String url = "/message";

        MockHttpServletResponse response = performPostRequest(url, messageRequest);

        Assert.assertEquals(201, response.getStatus());
        MessageResponse message = objectMapper.readValue(response.getContentAsString(), MessageResponse.class);
        Assert.assertEquals("channel", message.getData().getChannel());
        Assert.assertEquals("text", message.getData().getText());
        Assert.assertEquals("sender", message.getData().getSenderId());
        Assert.assertEquals("receiver", message.getData().getReceiverId());
        Assert.assertEquals("lyric", message.getData().getLyricId());
        Assert.assertEquals("content", message.getData().getContent());
        Assert.assertEquals("comment", message.getData().getCommentId());
        Assert.assertNotNull(message.getData().getObjectId());
    }
}
