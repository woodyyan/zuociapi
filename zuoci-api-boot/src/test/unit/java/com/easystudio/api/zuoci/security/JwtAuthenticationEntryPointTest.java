package com.easystudio.api.zuoci.security;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationEntryPointTest {
    private JwtAuthenticationEntryPoint point = new JwtAuthenticationEntryPoint();

    @Test
    public void shouldCommence() throws IOException {
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        AuthenticationException exception = new AuthenticationCredentialsNotFoundException("");
        point.commence(request, response, exception);
    }
}