package com.easystudio.api.zuoci.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Date;

import static org.easymock.EasyMock.expect;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(EasyMockRunner.class)
public class JwtAuthenticationTokenFilterTest extends EasyMockSupport {
    @TestSubject
    private JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Before
    public void setUp() throws Exception {
        setField(filter, "tokenHeader", "Authentication");
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    public void shouldNotDoFilterInternalWhenUsernameIsNull() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authentication", "Bearer abc");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();
        UserDetails value = new JwtUser(1L,
                "key",
                "",
                "secret",
                null,
                true,
                new Date());

        expect(jwtTokenUtil.getUsernameFromToken("abc")).andReturn(null);

        replayAll();
        filter.doFilterInternal(request, response, chain);
        verifyAll();
    }

    @Test
    public void shouldDoFilterInternal() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authentication", "Bearer abc");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();
        UserDetails value = new JwtUser(1L,
                "key",
                "",
                "secret",
                null,
                true,
                new Date());

        expect(jwtTokenUtil.getUsernameFromToken("abc")).andReturn("token");
        expect(userDetailsService.loadUserByUsername("token")).andReturn(value);
        expect(jwtTokenUtil.validateToken("abc", value)).andReturn(true);

        replayAll();
        filter.doFilterInternal(request, response, chain);
        verifyAll();
    }

    @Test
    public void shouldNotDoFilterInternalWhenValidateTokenFailed() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authentication", "Bearer abc");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();
        UserDetails value = new JwtUser(1L,
                "key",
                "",
                "secret",
                null,
                true,
                new Date());

        expect(jwtTokenUtil.getUsernameFromToken("abc")).andReturn("token");
        expect(userDetailsService.loadUserByUsername("token")).andReturn(value);
        expect(jwtTokenUtil.validateToken("abc", value)).andReturn(false);

        replayAll();
        filter.doFilterInternal(request, response, chain);
        verifyAll();
    }

    @Test
    public void shouldNotDoFilterInternalWhenThrowIllegalArgumentException() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authentication", "Bearer abc");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();
        UserDetails value = new JwtUser(1L,
                "key",
                "",
                "secret",
                null,
                true,
                new Date());

        expect(jwtTokenUtil.getUsernameFromToken("abc")).andThrow(new IllegalArgumentException());

        replayAll();
        filter.doFilterInternal(request, response, chain);
        verifyAll();
    }

    @Test
    public void shouldNotDoFilterInternalWhenThrowExpiredJwtException() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authentication", "Bearer abc");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();
        UserDetails value = new JwtUser(1L,
                "key",
                "",
                "secret",
                null,
                true,
                new Date());

        expect(jwtTokenUtil.getUsernameFromToken("abc")).andThrow(new ExpiredJwtException(new DefaultHeader(), new DefaultClaims(), ""));

        replayAll();
        filter.doFilterInternal(request, response, chain);
        verifyAll();
    }

    @Test
    public void shouldNotDoFilterInternalWhenAuthenticationIsNotNull() throws ServletException, IOException {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("", ""));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authentication", "Bearer abc");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();
        UserDetails value = new JwtUser(1L,
                "key",
                "",
                "secret",
                null,
                true,
                new Date());

        expect(jwtTokenUtil.getUsernameFromToken("abc")).andReturn("token");

        replayAll();
        filter.doFilterInternal(request, response, chain);
        verifyAll();
    }

    @Test
    public void shouldNotDoFilterInternalGivenHeaderIsNotStartWithBearer() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authentication", "abc");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = new MockFilterChain();
        UserDetails value = new JwtUser(1L,
                "key",
                "",
                "secret",
                null,
                true,
                new Date());

        replayAll();
        filter.doFilterInternal(request, response, chain);
        verifyAll();
    }
}