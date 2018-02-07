package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.security.JwtTokenUtil;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

@RunWith(EasyMockRunner.class)
public class AuthenticationControllerTest extends EasyMockSupport {

    @TestSubject
    private AuthenticationController controller = new AuthenticationController();

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Test
    public void shouldCreateAuthenticationToken() {
    }

    @Test
    public void refreshAndGetAuthenticationToken() {
    }
}