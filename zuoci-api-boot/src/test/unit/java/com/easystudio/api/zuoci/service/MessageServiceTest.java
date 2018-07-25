package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.Message;
import com.easystudio.api.zuoci.repository.MessageRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;

@RunWith(EasyMockRunner.class)
public class MessageServiceTest extends EasyMockSupport {
    @TestSubject
    private MessageService service = new MessageService();

    @Mock
    private MessageRepository repository;

    @Test
    public void shouldGetMessagesGivenReceiverIdAndPage() {
        String receiverId = "123";
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable page = new PageRequest(0, 20, sort);

        List<Message> content = new ArrayList<>();
        Message message = new Message();
        message.setChannel("comment");
        message.setCommentId("123");
        message.setContent("content");
        message.setLyricId("111");
        message.setReceiverId(receiverId);
        message.setText("text");
        message.setSenderId("sender");
        content.add(message);
        Page<Message> expectedMessages = new PageImpl<>(content, new PageRequest(0, 1), 1);

        expect(repository.findByReceiverId(receiverId, page)).andReturn(expectedMessages);

        replayAll();
        Page<Message> messages = service.searchMessage(receiverId, page);
        verifyAll();

        Assert.assertEquals(0, messages.getNumber());
        Assert.assertEquals(1, messages.getSize());
        Assert.assertEquals(1, messages.getTotalElements());
        Assert.assertEquals(1, messages.getTotalPages());
        Assert.assertEquals(1, messages.getContent().size());
        Assert.assertEquals("comment", messages.getContent().get(0).getChannel());
        Assert.assertEquals("content", messages.getContent().get(0).getContent());
        Assert.assertEquals("111", messages.getContent().get(0).getLyricId());
        Assert.assertEquals(receiverId, messages.getContent().get(0).getReceiverId());
        Assert.assertEquals("sender", messages.getContent().get(0).getSenderId());
        Assert.assertEquals("text", messages.getContent().get(0).getText());
    }
}