package com.easystudio.api.zuoci.configuration;

import com.easystudio.api.zuoci.security.JwtAuthenticationTokenFilter;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void shouldConfigureAuthentication() throws Exception {
        ObjectPostProcessor processor = EasyMock.mock(ObjectPostProcessor.class);
        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(processor);
        configuration.configureAuthentication(builder);
    }

    @Test
    public void shouldConfigureWithHttpSecurity() throws Exception {
        ObjectPostProcessor processor = EasyMock.mock(ObjectPostProcessor.class);
        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(processor);
        Map<Class<?>, Object> sharedObjects = new HashMap<>();
        HttpSecurity httpSecurity = new HttpSecurity(processor, builder, sharedObjects);
        configuration.configure(httpSecurity);
    }
}