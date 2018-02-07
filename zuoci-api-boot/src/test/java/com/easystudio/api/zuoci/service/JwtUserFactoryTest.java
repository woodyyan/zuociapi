package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.Authority;
import com.easystudio.api.zuoci.entity.User;
import com.easystudio.api.zuoci.security.JwtUser;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class JwtUserFactoryTest {

    @Test
    public void shouldCreateJwtUserGivenUser() {
        User user = new User();
        user.setId(123L);
        user.setEnabled(true);
        user.setDescription("desc");
        user.setPassword("password");
        user.setUsername("name");
        user.setLastPasswordResetDate(LocalDateTime.now());
        List<Authority> arthritis = new ArrayList<>();
        user.setAuthorities(arthritis);
        JwtUser jwtUser = JwtUserFactory.create(user);

        Assert.assertThat(jwtUser.getUsername(), is(user.getUsername()));
        Assert.assertThat(jwtUser.getPassword(), is(user.getPassword()));
        Assert.assertThat(jwtUser.getDescription(), is(user.getDescription()));
        Assert.assertThat(jwtUser.getId(), is(user.getId()));
        Assert.assertThat(jwtUser.getAuthorities().size(), is(0));
        Assert.assertThat(jwtUser.getLastPasswordResetDate(), lessThanOrEqualTo(new Date()));
    }
}