package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.DailyWord;
import com.easystudio.api.zuoci.model.DailyWordModel;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class DailyWordTranslatorTest {

    private DailyWordTranslator translator = new DailyWordTranslator();

    @Test
    public void toDailyWordResponse() {
        DailyWord dailyWord = new DailyWord();
        dailyWord.setCreatedTime(LocalDateTime.now());
        dailyWord.setDescription("description");
        dailyWord.setExplanation("explanation");
        dailyWord.setExplanationTitle("title");
        dailyWord.setLastModifiedTime(LocalDateTime.now());
        dailyWord.setPronunciation("pronunciation");
        dailyWord.setRhyme("rhyme");
        dailyWord.setWord("word");
        dailyWord.setObjectId(1L);
        dailyWord.setShareCount(2L);
        dailyWord.setViewCount(3L);
        dailyWord.setVisible(true);

        DailyWordModel model = translator.toDailyWordResponse(dailyWord);

        Assert.assertThat(model.getCreatedTime(), lessThanOrEqualTo(DateTime.now()));
        Assert.assertThat(model.getDescription(), is(dailyWord.getDescription()));
        Assert.assertThat(model.getExplanation(), is(dailyWord.getExplanation()));
        Assert.assertThat(model.getExplanationTitle(), is(dailyWord.getExplanationTitle()));
        Assert.assertThat(model.getLastModifiedTime(), lessThanOrEqualTo(DateTime.now()));
        Assert.assertThat(model.getPronunciation(), is(dailyWord.getPronunciation()));
        Assert.assertThat(model.getRhyme(), is(dailyWord.getRhyme()));
        Assert.assertThat(model.getShareCount(), is(2L));
        Assert.assertThat(model.getViewCount(), is(3L));
        Assert.assertThat(model.getWord(), is("word"));
        Assert.assertThat(model.getObjectId(), is(1L));
    }

    @Test
    public void toDailyWordEntity() {
        DailyWordModel model = new DailyWordModel();
        model.setCreatedTime(DateTime.now());
        model.setDescription("description");
        model.setExplanation("explanation");
        model.setExplanationTitle("title");
        model.setLastModifiedTime(DateTime.now());
        model.setPronunciation("pronunciation");
        model.setRhyme("rhyme");
        model.setWord("word");

        DailyWord dailyWord = translator.toDailyWordEntity(model);

        Assert.assertThat(dailyWord.getCreatedTime(), lessThanOrEqualTo(LocalDateTime.now()));
        Assert.assertThat(dailyWord.getDescription(), is(dailyWord.getDescription()));
        Assert.assertThat(dailyWord.getExplanation(), is(dailyWord.getExplanation()));
        Assert.assertThat(dailyWord.getExplanationTitle(), is(dailyWord.getExplanationTitle()));
        Assert.assertThat(dailyWord.getLastModifiedTime(), lessThanOrEqualTo(dailyWord.getLastModifiedTime()));
        Assert.assertThat(dailyWord.getPronunciation(), is(dailyWord.getPronunciation()));
        Assert.assertThat(dailyWord.getRhyme(), is(dailyWord.getRhyme()));
        Assert.assertThat(dailyWord.getShareCount(), is(0L));
        Assert.assertThat(dailyWord.getViewCount(), is(0L));
        Assert.assertThat(dailyWord.getWord(), is("word"));
        Assert.assertThat(dailyWord.isVisible(), is(true));
    }
}