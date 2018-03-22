package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.User;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public abstract class StubUserRepository implements UserRepository {
    @Override
    public User findByAppKey(String appKey) {
        User user = new User();
        user.setEnabled(true);
        user.setLastSecretResetDate(new LocalDateTime());
        user.setAppSecret("password");
        user.setAppKey("user");
        return user;
    }
}
