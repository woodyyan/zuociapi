package com.easystudio.api.zuoci.service.leancloud;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.easystudio.api.zuoci.entity.leancloud.FeaturedLyric;
import com.easystudio.api.zuoci.entity.leancloud.Lyric;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LeanCloudService {
    private static final Log logger = LogFactory.getLog(SpringApplication.class);

    // 每天凌晨4点更新一次， 取昨天的前5天的lyric, 计算factor(index)并存放到table
    public void updateRecommandLyricListOncePerDay() {

        Runnable runnable = () -> {
            try {
                startUpdate();
            } catch (AVException | ParseException e) {
                logger.error("[LEANCLOUD] ---- Update featured lyrics failed.", e);
                e.printStackTrace();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        long oneDay = 24 * 60 * 60 * 1000;
        long initDelay = getTimeMillis("04:00:00") - System.currentTimeMillis();
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
        service.scheduleWithFixedDelay(runnable, initDelay, oneDay, TimeUnit.MILLISECONDS);
    }

    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void startUpdate() throws AVException, ParseException {
        logger.info("[LEANCLOUD] ---- Start update featured lyrics");
        List<Lyric> recentLyrics = getRecentLyrics();
        logger.info("[LEANCLOUD] ---- Get recent lyrics, count: " + recentLyrics.size());
        for (Lyric lyric : recentLyrics) {
            calculateRecommandLyric(lyric);
        }
        logger.info("[LEANCLOUD] ---- Finished update featured lyrics");
    }

    private List<Lyric> getRecentLyrics() throws AVException {
        Date date = getDateBefore(-6);
        Date yesterday = getDateBefore(-1);

        AVQuery<Lyric> query = AVObject.getQuery(Lyric.class);
        query.whereEqualTo("isVisible", true);
        query.whereEqualTo("isValid", true);
        query.whereNotEqualTo("isDeleted", true);
        query.addDescendingOrder("viewCount");
        query.whereGreaterThan("createdAt", date);
        query.whereLessThan("createdAt", yesterday);
        query.limit(500);
        return query.find();
    }

    private Date getDateBefore(int days) {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    private void calculateRecommandLyric(Lyric lyric) throws AVException {
        int shareCount = getShareCount(lyric);
        int commentCount = getCommentCount(lyric);
        int starCount = getStarCount(lyric);
        int pointLikeCount = getPointLikeCount(lyric);
        int rhythmLikeCount = getRhythmLikeCount(lyric);
        int sightLikeCount = getSightLikeCount(lyric);
        int sentenceCount = getSentenceCount(lyric);
        int viewCount = lyric.getViewCount();

        //index计算方式
        int index = (starCount + sentenceCount) * 30
                + (rhythmLikeCount + pointLikeCount + sightLikeCount) * 30
                + viewCount * 20
                + shareCount * 10
                + commentCount * 10;
        logger.info("[LEANCLOUD] ---- Calculate index, lyric id: " + lyric.getObjectId());
        logger.info("[LEANCLOUD] ---- Calculate index: " + index);
        saveToRecommandLyric(index, lyric);
    }

    private void saveToRecommandLyric(int index, Lyric lyric) throws AVException {
        AVQuery<FeaturedLyric> query = AVQuery.getQuery(FeaturedLyric.class);
        query.whereEqualTo("lyric", lyric);
        FeaturedLyric featuredLyric = query.getFirst();
        if (featuredLyric != null) {
            int existingIndex = featuredLyric.getIndex();
            if (existingIndex != index) {
                featuredLyric.setIndex(index);
                featuredLyric.save();
                logger.info("[LEANCLOUD] ---- Updated existing index, lyric id: " + lyric.getObjectId());
            }
        } else {
            FeaturedLyric newFeaturedLyric = new FeaturedLyric();
            newFeaturedLyric.setIndex(index);
            newFeaturedLyric.setLyric(lyric);
            newFeaturedLyric.save();
            logger.info("[LEANCLOUD] ---- Saved new index, lyric id: " + lyric.getObjectId());
        }
    }

    private int getSentenceCount(Lyric lyric) throws AVException {
        AVQuery<AVObject> query = new AVQuery<>("LyricSentence");
        query.whereEqualTo("lyric", lyric);
        return query.count();
    }

    private int getSightLikeCount(Lyric lyric) throws AVException {
        AVQuery<AVObject> query = new AVQuery<>("LyricSightLike");
        query.whereEqualTo("lyricId", lyric);
        return query.count();
    }

    private int getRhythmLikeCount(Lyric lyric) throws AVException {
        AVQuery<AVObject> query = new AVQuery<>("LyricRhythmLike");
        query.whereEqualTo("lyricId", lyric);
        return query.count();
    }

    private int getPointLikeCount(Lyric lyric) throws AVException {
        AVQuery<AVObject> query = new AVQuery<>("LyricPointLike");
        query.whereEqualTo("lyricId", lyric);
        return query.count();
    }

    private int getStarCount(Lyric lyric) throws AVException {
        AVQuery<AVObject> query = new AVQuery<>("StarredLyric");
        query.whereEqualTo("lyricId", lyric);
        return query.count();
    }

    private int getCommentCount(Lyric lyric) throws AVException {
        AVQuery<AVObject> query = new AVQuery<>("Comment");
        query.whereEqualTo("lyricId", lyric);
        query.whereEqualTo("isVisible", true);
        query.whereNotEqualTo("isDeleted", true);
        return query.count();
    }

    private int getShareCount(Lyric lyric) throws AVException {
        AVQuery<AVObject> query = new AVQuery<>("SharedLyricInfo");
        query.whereEqualTo("sharedLyricId", lyric);
        return query.count();
    }
}