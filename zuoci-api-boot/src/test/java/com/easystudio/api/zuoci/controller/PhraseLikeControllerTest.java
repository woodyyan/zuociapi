package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.PhraseLikeRequest;
import com.easystudio.api.zuoci.service.PhraseLikeService;
import com.easystudio.api.zuoci.validate.PhraseLikeValidator;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EasyMockRunner.class)
public class PhraseLikeControllerTest extends EasyMockSupport {

    @TestSubject
    private PhraseLikeController controller = new PhraseLikeController();

    @Mock
    private PhraseLikeValidator validator;

    @Mock
    private PhraseLikeService service;

    @Test
    public void shouldAddFeelingLikeGivenLikeRequest() {
        PhraseLikeRequest request = new PhraseLikeRequest();

        validator.validate(request);
        service.addLike(request);

        controller.addLike(request);
    }
}