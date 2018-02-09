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
        String appKey = "key";
        String appSecret = "secret";
        User user = new User();
        user.setAppKey(appKey);
        user.setAppSecret(appSecret);
        user.setEnabled(true);
        user.setLastSecretResetDate(LocalDateTime.now());
        List<Authority> authorities = new ArrayList<>();
        user.setAuthorities(authorities);

        expect(repository.findByAppKey(appKey)).andReturn(user);

        replayAll();
        UserDetails userDetails = service.loadUserByUsername(appKey);
        verifyAll();

        Assert.assertThat(userDetails.getUsername(), is(appKey));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowUsernameNotFoundExceptionWhenUsernameNotExist() {
        String appKey = "name";

        expect(repository.findByAppKey(appKey)).andReturn(null);

        service.loadUserByUsername(appKey);
    }
}