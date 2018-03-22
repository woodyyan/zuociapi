package com.easystudio.api.zuoci.configuration;

import com.easystudio.api.zuoci.entity.Phrase;
import com.easystudio.api.zuoci.entity.User;
import com.easystudio.api.zuoci.repository.PhraseRepository;
import com.easystudio.api.zuoci.repository.UserRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;

public class DbFixture {

    @Autowired
    private PhraseRepository phraseRepository;

    @Autowired
    private UserRepository userRepository;

    public void addUser() {
        User user = new User();
        user.setAppKey("key");
        user.setAppSecret("secret");
        user.setLastSecretResetDate(LocalDateTime.now());
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void addPhrase(String content) {
        Phrase phrase = new Phrase();
        phrase.setContent(content);
        phrase.setValid(true);
        phrase.setVisible(true);
        phrase.setAuthorId("123");
        phrase.setLocation("beijing");
        phrase.setViewCount(1L);
        phrase.setCreatedTime(LocalDateTime.now());
        phrase.setLastModifiedTime(LocalDateTime.now());
        phraseRepository.save(phrase);
    }
}
