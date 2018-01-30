package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.FeelingLike;
import com.easystudio.api.zuoci.entity.InterestingLike;
import com.easystudio.api.zuoci.entity.NormalLike;
import com.easystudio.api.zuoci.model.LikeType;
import com.easystudio.api.zuoci.model.PhraseLikeRequest;
import com.easystudio.api.zuoci.model.PhraseLikeResponse;
import com.easystudio.api.zuoci.repository.PhraseFeelingLikeRepository;
import com.easystudio.api.zuoci.repository.PhraseInterestingLikeRepository;
import com.easystudio.api.zuoci.repository.PhraseNormalLikeRepository;
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
    private PhraseNormalLikeRepository normalLikeRepository;

    @Mock
    private PhraseInterestingLikeRepository interestingLikeRepository;

    @Mock
    private PhraseFeelingLikeRepository feelingLikeRepository;

    @Test
    public void shouldAddLikeCountGivenExistingNormalLikeWhenNormalLike() {
        PhraseLikeRequest request = new PhraseLikeRequest();
        request.setUserId("123");
        request.setPhraseId(1L);
        request.setLikeType(LikeType.NormalLike);

        List<NormalLike> normalLikes = new ArrayList<>();
        NormalLike normalLike = new NormalLike();
        normalLike.setUserId("userId");
        normalLike.setPhraseId(1L);
        normalLike.setLikeCount(1L);
        normalLikes.add(normalLike);
        expect(normalLikeRepository.findByPhraseId(request.getPhraseId())).andReturn(normalLikes);
        expect(normalLikeRepository.save(normalLike)).andReturn(normalLike);

        replayAll();
        service.addLike(request);
        verifyAll();

        Assert.assertThat(normalLike.getLikeCount(), is(2L));
        Assert.assertThat(normalLike.getLastModifiedTime(), lessThanOrEqualTo(LocalDateTime.now()));
    }

    @Test
    public void shouldAddLikeCountGivenExistingInterestingLikeWhenInterestingLike() {
        PhraseLikeRequest request = new PhraseLikeRequest();
        request.setUserId("123");
        request.setPhraseId(1L);
        request.setLikeType(LikeType.InterestingLike);

        List<InterestingLike> interestingLikes = new ArrayList<>();
        InterestingLike interestingLike = new InterestingLike();
        interestingLike.setUserId("userId");
        interestingLike.setPhraseId(1L);
        interestingLike.setLikeCount(1L);
        interestingLikes.add(interestingLike);
        expect(interestingLikeRepository.findByPhraseId(request.getPhraseId())).andReturn(interestingLikes);
        expect(interestingLikeRepository.save(interestingLike)).andReturn(interestingLike);

        replayAll();
        service.addLike(request);
        verifyAll();

        Assert.assertThat(interestingLike.getLikeCount(), is(2L));
        Assert.assertThat(interestingLike.getLastModifiedTime(), lessThanOrEqualTo(LocalDateTime.now()));
    }

    @Test
    public void shouldAddLikeCountGivenExistingFeelingLikeWhenFeelingLike() {
        PhraseLikeRequest request = new PhraseLikeRequest();
        request.setUserId("123");
        request.setPhraseId(1L);
        request.setLikeType(LikeType.FeelingLike);

        List<FeelingLike> feelingLikes = new ArrayList<>();
        FeelingLike feelingLike = new FeelingLike();
        feelingLike.setUserId("userId");
        feelingLike.setPhraseId(1L);
        feelingLike.setLikeCount(1L);
        feelingLikes.add(feelingLike);
        expect(feelingLikeRepository.findByPhraseId(request.getPhraseId())).andReturn(feelingLikes);
        expect(feelingLikeRepository.save(feelingLike)).andReturn(feelingLike);

        replayAll();
        service.addLike(request);
        verifyAll();

        Assert.assertThat(feelingLike.getLikeCount(), is(2L));
        Assert.assertThat(feelingLike.getLastModifiedTime(), lessThanOrEqualTo(LocalDateTime.now()));
    }

    @Test
    public void shouldGetPhraseLikeCountGivenPhraseId() {
        Long phraseId = 123L;

        expect(normalLikeRepository.countByPhraseId(phraseId)).andReturn(10L);
        expect(feelingLikeRepository.countByPhraseId(phraseId)).andReturn(11L);
        expect(interestingLikeRepository.countByPhraseId(phraseId)).andReturn(12L);

        replayAll();
        ResponseEntity<PhraseLikeResponse> responseEntity = service.getLikeCount(phraseId);
        verifyAll();

        Assert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(responseEntity.getBody().getPhraseId(), is(phraseId));
        Assert.assertThat(responseEntity.getBody().getNormalLike().getCount(), is(10L));
        Assert.assertThat(responseEntity.getBody().getFeelingLike().getCount(), is(11L));
        Assert.assertThat(responseEntity.getBody().getInterestingLike().getCount(), is(12L));
    }
}