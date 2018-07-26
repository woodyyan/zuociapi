package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Message;
import com.easystudio.api.zuoci.model.MessageData;
import com.easystudio.api.zuoci.model.MessageRequest;
import com.easystudio.api.zuoci.model.Messages;
import com.easystudio.api.zuoci.repository.MessageRepository;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class MessageControllerIntegrationTest extends AbstractControllerIntegrationTest {
    @MockBean
    private MessageRepository messageRepository;

    @Test
    public void shouldGetMessagesGivenReceiverIdAndPage() throws Exception {
        String url = "/message?receiverId=123";

        String receiverId = "123";
        Pageable page = new PageRequest(0, 1);
        List<Message> content = new ArrayList<>();
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
        content.add(message);
        Page<Message> pagedMessage = new PageImpl<>(content, page, 1);
        when(messageRepository.findByReceiverId(Mockito.anyString(), Mockito.any(Pageable.class)))
                .thenReturn(pagedMessage);

        MockHttpServletResponse response = performGetRequest(url);

        Assert.assertEquals(200, response.getStatus());
        Messages messages = objectMapper.readValue(response.getContentAsString(), Messages.class);
        Assert.assertEquals(0, messages.getMeta().getPageNumber());
        Assert.assertEquals(1L, messages.getMeta().getTotalPages());
        Assert.assertEquals(1L, messages.getMeta().getPageSize());
        Assert.assertEquals(1L, messages.getMeta().getTotalElements());
        Assert.assertEquals(1, messages.getData().size());
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
        data.setLyricId("lyric");
        data.setChannel("channel");
        data.setContent("content");
        data.setText("text");
        messageRequest.setData(data);
        String url = "/message";

        when(messageRepository.save(Mockito.any(Message.class))).thenReturn(null);

        MockHttpServletResponse response = performPostRequest(url, messageRequest);

        Assert.assertEquals(201, response.getStatus());
    }
}
