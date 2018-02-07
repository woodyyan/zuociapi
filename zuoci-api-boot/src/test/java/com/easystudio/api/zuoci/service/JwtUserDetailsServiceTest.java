package com.easystudio.api.zuoci.service;

import com.easystudio.api.zuoci.entity.Authority;
import com.easystudio.api.zuoci.entity.User;
import com.easystudio.api.zuoci.repository.UserRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;

@RunWith(EasyMockRunner.class)
public class JwtUserDetailsServiceTest extends EasyMockSupport {

    @TestSubject
    private JwtUserDetailsService service = new JwtUserDetailsService();

    @Mock
    private UserRepository repository;

    @Test
    public void shouldLoadUserByUsernameGiveUsername() {
        String username = "name";
        String password = "pass";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);
        user.setLastPasswordResetDate(LocalDateTime.now());
        List<Authority> authorites = new ArrayList<>();
        user.setAuthorities(authorites);

        expect(repository.findByUsername(username)).andReturn(user);

        replayAll();
        UserDetails userDetails = service.loadUserByUsername(username);
        verifyAll();

        Assert.assertThat(userDetails.getUsername(), is(username));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowUsernameNotFoundExceptionWhenUsernameNotExist() {
        String username = "name";

        expect(repository.findByUsername(username)).andReturn(null);

        service.loadUserByUsername(username);
    }
}