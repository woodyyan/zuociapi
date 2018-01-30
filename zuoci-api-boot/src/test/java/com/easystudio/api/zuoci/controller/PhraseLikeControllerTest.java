package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.*;
import com.easystudio.api.zuoci.service.PhraseLikeService;
import com.easystudio.api.zuoci.validate.PhraseLikeValidator;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;

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

    @Test
    public void shouldGetLikeCountGivePhraseId() {
        Long phraseId = 123L;
        PhraseLikeResponse body = new PhraseLikeResponse();
        body.setPhraseId(phraseId);
        FeelingLike feelingLike = new FeelingLike();
        feelingLike.setCount(10L);
        body.setFeelingLike(feelingLike);
        InterestingLike interestingLike = new InterestingLike();
        interestingLike.setCount(12L);
        body.setInterestingLike(interestingLike);
        NormalLike normalLike = new NormalLike();
        normalLike.setCount(13L);
        body.setNormalLike(normalLike);
        ResponseEntity<PhraseLikeResponse> response = new ResponseEntity<>(body, HttpStatus.OK);

        expect(service.getLikeCount(phraseId)).andReturn(response);

        replayAll();
        ResponseEntity<PhraseLikeResponse> responseResponseEntity = controller.getLikeCount(phraseId);
        verifyAll();

        Assert.assertThat(responseResponseEntity.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(responseResponseEntity.getBody().getPhraseId(), is(123L));
        Assert.assertThat(responseResponseEntity.getBody().getFeelingLike().getCount(), is(10L));
        Assert.assertThat(responseResponseEntity.getBody().getInterestingLike().getCount(), is(12L));
        Assert.assertThat(responseResponseEntity.getBody().getNormalLike().getCount(), is(13L));
    }
}