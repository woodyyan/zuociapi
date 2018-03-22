package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.InspirationLike;
import com.easystudio.api.zuoci.entity.InterestingLike;
import com.easystudio.api.zuoci.entity.ResonanceLike;
import com.easystudio.api.zuoci.model.LikeType;
import com.easystudio.api.zuoci.model.PhraseLikeRequest;
import com.easystudio.api.zuoci.model.PhraseLikeResponse;
import com.easystudio.api.zuoci.repository.PhraseInspirationLikeRepository;
import com.easystudio.api.zuoci.repository.PhraseInterestingLikeRepository;
import com.easystudio.api.zuoci.repository.PhraseResonanceLikeRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

@RunWith(EasyMockRunner.class)
public class PhraseLikeServiceTest extends EasyMockSupport {

    @TestSubject
    private PhraseLikeService service = new PhraseLikeService();

    @Mock
    private PhraseResonanceLikeRepository resonanceLikeRepository;

    @Mock
    private PhraseInterestingLikeRepository interestingLikeRepository;

    @Mock
    private PhraseInspirationLikeRepository inspirationLikeRepository;

    @Test
    public void shouldGetPhraseLikeCountGivenPhraseId() {
        Long phraseId = 123L;

        expect(resonanceLikeRepository.countByPhraseId(phraseId)).andReturn(10L);
        expect(inspirationLikeRepository.countByPhraseId(phraseId)).andReturn(11L);
        expect(interestingLikeRepository.countByPhraseId(phraseId)).andReturn(12L);

        replayAll();
        ResponseEntity<PhraseLikeResponse> responseEntity = service.getLikeCount(phraseId);
        verifyAll();

        Assert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(responseEntity.getBody().getPhraseId(), is(phraseId));
        Assert.assertThat(responseEntity.getBody().getResonanceLike().getCount(), is(10L));
        Assert.assertThat(responseEntity.getBody().getInspirationLike().getCount(), is(11L));
        Assert.assertThat(responseEntity.getBody().getInterestingLike().getCount(), is(12L));
    }
}