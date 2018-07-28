package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.MessageData;
import com.easystudio.api.zuoci.model.MessageRequest;
import com.easystudio.api.zuoci.model.MessageResponse;
import com.easystudio.api.zuoci.model.Messages;
import com.easystudio.api.zuoci.service.MessageService;
import com.easystudio.api.zuoci.validate.MessageValidator;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    private MessageValidator validator;

    @Test
    public void shouldReturnMessageResponseGivenReceiverIdAndPage() {
        String receiverId = "123";
        Pageable page = new PageRequest(0, 20);

        Messages messages = new Messages();
        List<MessageData> data = new ArrayList<>();
        MessageData message = new MessageData();
        data.add(message);
        messages.setData(data);
        expect(service.searchMessage(receiverId, page)).andReturn(messages);

        replayAll();
        ResponseEntity<Messages> responseEntity = controller.searchMessage(receiverId, page);
        verifyAll();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(1, responseEntity.getBody().getData().size());
    }

    @Test
    public void shouldReturn201WhenCreateAMessage() {
        MessageRequest request = new MessageRequest();

        validator.validate(request);
        MessageResponse expectedMessage = new MessageResponse();
        expectedMessage.getData().setObjectId(1L);
        expect(service.createMessage(request)).andReturn(expectedMessage);

        replayAll();
        ResponseEntity<MessageResponse> entity = controller.createMessage(request);
        verifyAll();

        Assert.assertTrue(entity.getStatusCode() == HttpStatus.CREATED);
        Assert.assertTrue(entity.getBody().getData().getObjectId() == 1);
    }
}