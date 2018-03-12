package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.model.JwtAuthenticationRequest;
import com.easystudio.api.zuoci.model.JwtAuthenticationResponse;
import com.easystudio.api.zuoci.security.JwtTokenUtil;
import com.easystudio.api.zuoci.security.JwtUser;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.util.ReflectionTestUtils.setField;

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
        JwtAuthenticationRequest request = new JwtAuthenticationRequest("abc", "123");
        String platform = "";

        ResponseEntity<?> authenticationToken = controller.createAuthenticationToken(request, platform);

        Assert.assertThat(authenticationToken.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(authenticationToken.hasBody(), is(true));
    }

    @Test
    public void refreshAndGetAuthenticationToken() {
        setField(controller, "tokenHeader", "Authorization");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer value");

        expect(jwtTokenUtil.getUsernameFromToken("value")).andReturn("username");
        Date date = new Date();
        UserDetails user = new JwtUser(1L, "key", "description", "secret", null, true, date);
        expect(userDetailsService.loadUserByUsername("username")).andReturn(user);
        expect(jwtTokenUtil.canTokenBeRefreshed("value", date)).andReturn(true);
        expect(jwtTokenUtil.refreshToken("value")).andReturn("new value");

        replayAll();
        ResponseEntity<?> responseEntity = controller.refreshAndGetAuthenticationToken(request);
        verifyAll();

        Assert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        Assert.assertThat(((JwtAuthenticationResponse) responseEntity.getBody()).getToken(), is("new value"));
    }

    @Test
    public void cannotRefreshAuthenticationToken() {
        setField(controller, "tokenHeader", "Authorization");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer value");

        expect(jwtTokenUtil.getUsernameFromToken("value")).andReturn("username");
        Date date = new Date();
        UserDetails user = new JwtUser(1L, "key", "description", "secret", null, true, date);
        expect(userDetailsService.loadUserByUsername("username")).andReturn(user);
        expect(jwtTokenUtil.canTokenBeRefreshed("value", date)).andReturn(false);

        replayAll();
        ResponseEntity<?> responseEntity = controller.refreshAndGetAuthenticationToken(request);
        verifyAll();

        Assert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        Assert.assertThat(responseEntity.hasBody(), is(false));
    }
}