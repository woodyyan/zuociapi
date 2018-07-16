package com.easystudio.api.zuoci.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class JwtUserTest {
    private JwtUser user;

    @Before
    public void setUp() throws Exception {
        user = new JwtUser(1L, "key", "description", "secret", null, true, new Date());
    }

    @Test
    public void shouldReturnTrueWhenIsAccountNonLocked() {
        Assert.assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void shouldReturnTrueWhenIsAccountNonExpired() {
        Assert.assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void shouldReturnTrueWhenIsCredentialsNonExpired() {
        Assert.assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    public void shouldReturnTrueWhenIsEnabled() {
        Assert.assertTrue(user.isEnabled());
    }
}