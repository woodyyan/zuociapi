package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.DailyWordModel;
import com.easystudio.api.zuoci.service.DailyWordService;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

@RunWith(EasyMockRunner.class)
public class DailyWordControllerTest extends EasyMockSupport {

    @TestSubject
    private DailyWordController controller = new DailyWordController();

    @Mock
    private DailyWordService service;

    @Test
    public void shouldGetDailyWordForToday() {
        DailyWordModel content = new DailyWordModel();
        content.setCreatedTime(DateTime.now());
        content.setDescription("description");
        content.setExplanation("explanation");
        content.setExplanationTitle("title");
        content.setLastModifiedTime(DateTime.now());
        content.setObjectId(1L);
        content.setPronunciation("pronunciation");
        content.setRhyme("rhyme");
        content.setShareCount(10L);
        content.setViewCount(123L);
        content.setWord("word");
        ResponseEntity<DailyWordModel> dailyWord = new ResponseEntity<>(content, HttpStatus.OK);

        expect(service.getDailyWord()).andReturn(dailyWord);

        replayAll();
        ResponseEntity<DailyWordModel> responseEntity = controller.getDailyWord();
        verifyAll();

        Assert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(responseEntity.getBody().getObjectId(), is(1L));
        Assert.assertThat(responseEntity.getBody().getShareCount(), is(10L));
        Assert.assertThat(responseEntity.getBody().getExplanation(), is("explanation"));
        Assert.assertThat(responseEntity.getBody().getDescription(), is("description"));
        Assert.assertThat(responseEntity.getBody().getExplanationTitle(), is("title"));
        Assert.assertThat(responseEntity.getBody().getWord(), is("word"));
        Assert.assertThat(responseEntity.getBody().getViewCount(), is(123L));
        Assert.assertThat(responseEntity.getBody().getPronunciation(), is("pronunciation"));
        Assert.assertThat(responseEntity.getBody().getRhyme(), is("rhyme"));
        Assert.assertThat(responseEntity.getBody().getCreatedTime(), lessThanOrEqualTo(DateTime.now()));
        Assert.assertThat(responseEntity.getBody().getLastModifiedTime(), lessThanOrEqualTo(DateTime.now()));
    }

    @Test
    public void shouldCreateDailyWord() {
        DailyWordModel dailyWord = new DailyWordModel();

        ResponseEntity<?> responseEntity = controller.createDailyWord(dailyWord);

        Assert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
    }
}