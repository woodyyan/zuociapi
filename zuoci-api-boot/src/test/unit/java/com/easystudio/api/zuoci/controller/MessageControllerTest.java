package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.entity.Message;
import com.easystudio.api.zuoci.model.MessageData;
import com.easystudio.api.zuoci.model.Messages;
import com.easystudio.api.zuoci.service.MessageService;
import com.easystudio.api.zuoci.translator.MessageTranslator;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;

@RunWith(EasyMockRunner.class)
public class MessageControllerTest extends EasyMockSupport {
    @TestSubject
    private MessageController controller = new MessageController();

    @Mock
    private MessageService service;

    @Mock
    private MessageTranslator translator;

    @Test
    public void shouldReturnMessageresponseGivenReceiverIdAndPage() {
        String receiverId = "123";
        Pageable page = new PageRequest(0, 20);

        List<Message> content = new ArrayList<>();
        Page<Message> pagedMessages = new PageImpl<>(content, page, 1);
        expect(service.searchMessage(receiverId, page)).andReturn(pagedMessages);
        Messages body = new Messages();
        List<MessageData> data = new ArrayList<>();
        MessageData message = new MessageData();
        data.add(message);
        body.setData(data);
        ResponseEntity<Messages> entity = new ResponseEntity<>(body, HttpStatus.OK);
        expect(translator.toMessageResponse(pagedMessages)).andReturn(entity);

        replayAll();
        ResponseEntity<Messages> responseEntity = controller.searchMessage(receiverId, page);
        verifyAll();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getData().size());
    }
}