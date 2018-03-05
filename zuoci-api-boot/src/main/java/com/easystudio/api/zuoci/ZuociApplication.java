package com.easystudio.api.zuoci;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.easystudio.api.zuoci.entity.leancloud.FeaturedLyric;
import com.easystudio.api.zuoci.entity.leancloud.Lyric;
import com.easystudio.api.zuoci.service.leancloud.LeanCloudService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class ZuociApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuociApplication.class, args);
        initLeanCloudInit();
    }

    private static void initLeanCloudInit() {
        AVObject.registerSubclass(Lyric.class);
        AVObject.registerSubclass(FeaturedLyric.class);
        AVOSCloud.initialize("zj7k6dju1rydqnm05eupl1k196n34w05mpf3g4d276vd7e4s",
                "jc6pgwggwhv4h6svcieenqfoao7slkzniqbffbrzs4addfni",
                "k1k4rk74sem7aiiz9kt48mfkxqnkf78q83p6vgeu0h6aqhkx");
        LeanCloudService service = new LeanCloudService();
        service.updateRecommandLyricListOncePerDay();

    }
}