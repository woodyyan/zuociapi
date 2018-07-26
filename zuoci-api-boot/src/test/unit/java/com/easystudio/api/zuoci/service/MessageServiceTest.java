package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.Message;
import com.easystudio.api.zuoci.model.MessageData;
import com.easystudio.api.zuoci.model.Messages;
import com.easystudio.api.zuoci.repository.MessageRepository;
import com.easystudio.api.zuoci.translator.MessageTranslator;
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

    @Mock
    private MessageTranslator translator;

    @Test
    public void shouldGetMessagesGivenReceiverIdAndPage() {
        String receiverId = "123";
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable page = new PageRequest(0, 20, sort);

        List<Message> content = new ArrayList<>();
        Page<Message> expectedPages = new PageImpl<>(content, new PageRequest(0, 1), 1);

        Messages expectedMessages = new Messages();
        List<MessageData> data = new ArrayList<>();
        MessageData message = new MessageData();
        message.setText("text");
        message.setContent("content");
        message.setChannel("comment");
        message.setLyricId("111");
        message.setCommentId("123");
        message.setReceiverId(receiverId);
        message.setSenderId("sender");
        data.add(message);
        expectedMessages.setData(data);

        expect(repository.findByReceiverId(receiverId, page)).andReturn(expectedPages);
        expect(translator.toMessageResponse(expectedPages)).andReturn(expectedMessages);

        replayAll();
        Messages messages = service.searchMessage(receiverId, page);
        verifyAll();

        Assert.assertEquals(1, messages.getData().size());
        Assert.assertEquals("comment", messages.getData().get(0).getChannel());
        Assert.assertEquals("content", messages.getData().get(0).getContent());
        Assert.assertEquals("111", messages.getData().get(0).getLyricId());
        Assert.assertEquals(receiverId, messages.getData().get(0).getReceiverId());
        Assert.assertEquals("sender", messages.getData().get(0).getSenderId());
        Assert.assertEquals("text", messages.getData().get(0).getText());
    }
}