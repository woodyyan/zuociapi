package com.easystudio.api.zuoci.entity.leancloud;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("Lyric")
public class Lyric extends AVObject {
    public int getViewCount() {
        return getInt("viewCount");
    }

    public void setViewCount(int value) {
        put("viewCount", value);
    }
}