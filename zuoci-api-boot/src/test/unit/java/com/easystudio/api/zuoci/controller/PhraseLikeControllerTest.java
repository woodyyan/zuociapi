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

        validator.validate(1L, request);
        service.addLike(request);

        controller.addLike(1L, request);
    }

    @Test
    public void shouldGetLikeCountGivePhraseId() {
        Long phraseId = 123L;
        PhraseLikeResponse body = new PhraseLikeResponse();
        body.setPhraseId(phraseId);
        InspirationLike inspirationLike = new InspirationLike();
        inspirationLike.setCount(10L);
        body.setInspirationLike(inspirationLike);
        InterestingLike interestingLike = new InterestingLike();
        interestingLike.setCount(12L);
        body.setInterestingLike(interestingLike);
        ResonanceLike resonanceLike = new ResonanceLike();
        resonanceLike.setCount(13L);
        body.setResonanceLike(resonanceLike);
        ResponseEntity<PhraseLikeResponse> response = new ResponseEntity<>(body, HttpStatus.OK);

        expect(service.getLikeCount(phraseId)).andReturn(response);

        replayAll();
        ResponseEntity<PhraseLikeResponse> responseResponseEntity = controller.getLikeCount(phraseId);
        verifyAll();

        Assert.assertThat(responseResponseEntity.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(responseResponseEntity.getBody().getPhraseId(), is(123L));
        Assert.assertThat(responseResponseEntity.getBody().getInspirationLike().getCount(), is(10L));
        Assert.assertThat(responseResponseEntity.getBody().getInterestingLike().getCount(), is(12L));
        Assert.assertThat(responseResponseEntity.getBody().getResonanceLike().getCount(), is(13L));
    }
}