package com.easystudio.api.zuoci.configuration;

import com.easystudio.api.zuoci.security.JwtAuthenticationTokenFilter;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class WebSecurityConfigurationTest {
    private WebSecurityConfiguration configuration = new WebSecurityConfiguration();

    @Test
    public void shouldCreatePasswordEncoderBean() {
        PasswordEncoder passwordEncoder = configuration.passwordEncoder();
        Assert.assertNotNull(passwordEncoder);
        Assert.assertEquals(passwordEncoder.getClass(), BCryptPasswordEncoder.class);
    }

    @Test
    public void shouldCreateAuthenticationTokenFilterBean() throws Exception {
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = configuration.authenticationTokenFilterBean();
        Assert.assertNotNull(jwtAuthenticationTokenFilter);
    }
}