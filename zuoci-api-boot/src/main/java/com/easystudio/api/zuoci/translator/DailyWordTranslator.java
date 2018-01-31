package com.easystudio.api.zuoci.translator;

import com.easystudio.api.zuoci.entity.DailyWord;
import com.easystudio.api.zuoci.model.DailyWordModel;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class DailyWordTranslator {
    public DailyWordModel toDailyWordResponse(DailyWord dailyWord) {
        DailyWordModel dailyWordModel = new DailyWordModel();
        dailyWordModel.setWord(dailyWord.getWord());
        dailyWordModel.setViewCount(dailyWord.getViewCount());
        dailyWordModel.setShareCount(dailyWord.getShareCount());
        dailyWordModel.setRhyme(dailyWord.getRhyme());
        dailyWordModel.setPronunciation(dailyWord.getPronunciation());
        dailyWordModel.setLastModifiedTime(dailyWord.getLastModifiedTime().toDateTime());
        dailyWordModel.setExplanationTitle(dailyWord.getExplanationTitle());
        dailyWordModel.setDescription(dailyWord.getDescription());
        dailyWordModel.setCreatedTime(dailyWord.getCreatedTime().toDateTime());
        dailyWordModel.setObjectId(dailyWord.getObjectId());
        dailyWordModel.setExplanation(dailyWord.getExplanation());
        return dailyWordModel;
    }

    public DailyWord toDailyWordEntity(DailyWordModel dailyWordModel) {
        DailyWord dailyWord = new DailyWord();
        dailyWord.setCreatedTime(LocalDateTime.now());
        dailyWord.setLastModifiedTime(LocalDateTime.now());
        dailyWord.setDescription(dailyWordModel.getDescription());
        dailyWord.setExplanation(dailyWordModel.getExplanation());
        dailyWord.setExplanationTitle(dailyWordModel.getExplanationTitle());
        dailyWord.setPronunciation(dailyWordModel.getPronunciation());
        dailyWord.setRhyme(dailyWordModel.getRhyme());
        dailyWord.setShareCount(0L);
        dailyWord.setViewCount(0L);
        dailyWord.setVisible(true);
        dailyWord.setWord(dailyWordModel.getWord());
        return dailyWord;
    }
}
