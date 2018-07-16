package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.LikeType;
import com.easystudio.api.zuoci.model.PhraseLikeRequest;
import com.easystudio.api.zuoci.model.PhraseLikeResponse;
import com.easystudio.api.zuoci.repository.PhraseInspirationLikeRepository;
import com.easystudio.api.zuoci.repository.PhraseInterestingLikeRepository;
import com.easystudio.api.zuoci.repository.PhraseResonanceLikeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class PhraseLikeControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @MockBean
    private PhraseInterestingLikeRepository interestingLikeRepository;

    @MockBean
    private PhraseInspirationLikeRepository inspirationLikeRepository;

    @MockBean
    private PhraseResonanceLikeRepository resonanceLikeRepository;

    @Test
    public void shouldGetLikeCount() throws Exception {
        when(resonanceLikeRepository.countByPhraseId(123L)).thenReturn(1L);
        when(interestingLikeRepository.countByPhraseId(123L)).thenReturn(2L);
        when(inspirationLikeRepository.countByPhraseId(123L)).thenReturn(3L);

        MockHttpServletResponse response = performGetRequest("/phrase/123/like");

        Assert.assertEquals(200, response.getStatus());
        PhraseLikeResponse phraseLikeResponse = objectMapper.readValue(response.getContentAsString(), PhraseLikeResponse.class);
        Assert.assertThat(phraseLikeResponse.getPhraseId(), is(123L));
        Assert.assertThat(phraseLikeResponse.getResonanceLike().getCount(), is(1L));
        Assert.assertThat(phraseLikeResponse.getInterestingLike().getCount(), is(2L));
        Assert.assertThat(phraseLikeResponse.getInspirationLike().getCount(), is(3L));
    }

    @Test
    public void shouldGetLikeCountWhenNoLike() throws Exception {
        when(resonanceLikeRepository.countByPhraseId(123L)).thenReturn(0L);
        when(interestingLikeRepository.countByPhraseId(123L)).thenReturn(0L);
        when(inspirationLikeRepository.countByPhraseId(123L)).thenReturn(0L);

        MockHttpServletResponse response = performGetRequest("/phrase/123/like");

        Assert.assertEquals(200, response.getStatus());
        PhraseLikeResponse phraseLikeResponse = objectMapper.readValue(response.getContentAsString(), PhraseLikeResponse.class);
        Assert.assertThat(phraseLikeResponse.getPhraseId(), is(123L));
        Assert.assertThat(phraseLikeResponse.getResonanceLike().getCount(), is(0L));
        Assert.assertThat(phraseLikeResponse.getInterestingLike().getCount(), is(0L));
        Assert.assertThat(phraseLikeResponse.getInspirationLike().getCount(), is(0L));
    }

    @Test
    public void shouldAddResonanceLikeCountGivenPhraseIdAndRequest() throws Exception {
        PhraseLikeRequest body = new PhraseLikeRequest();
        body.setUserId("abc");
        body.setPhraseId(123L);
        body.setLikeType(LikeType.ResonanceLike);
        MockHttpServletResponse response = performPostRequest("/phrase/123/like", body);

        Assert.assertEquals(201, response.getStatus());
    }

    @Test
    public void shouldAddInspirationLikeCountGivenPhraseIdAndRequest() throws Exception {
        PhraseLikeRequest body = new PhraseLikeRequest();
        body.setUserId("abc");
        body.setPhraseId(123L);
        body.setLikeType(LikeType.InspirationLike);
        MockHttpServletResponse response = performPostRequest("/phrase/123/like", body);

        Assert.assertEquals(201, response.getStatus());
    }

    @Test
    public void shouldAddInterestingLikeCountGivenPhraseIdAndRequest() throws Exception {
        PhraseLikeRequest body = new PhraseLikeRequest();
        body.setUserId("abc");
        body.setPhraseId(123L);
        body.setLikeType(LikeType.InterestingLike);
        MockHttpServletResponse response = performPostRequest("/phrase/123/like", body);

        Assert.assertEquals(201, response.getStatus());
    }
}
