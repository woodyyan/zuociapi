package com.easystudio.api.zuoci.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class JwtTokenUtilTest {
    private static final String expiredtoken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNTIxMDQwNzE4LCJpYXQiOjE1MjA0MzU5MTh9.yzMTijqNetg1HROuz4AOR4f24b9qiE30-ifqmwtU-tdu-7j8eZQBfG7s6rgOyoqDPCijhLVoRsLBE0Vnt9MiNg";
    //这个token过期时间是100年后
    private static final String validToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjo0NDI0NzY5OTM3LCJpYXQiOjE1MjE3Mjk5Mzd9.tb013UptHLas3JT3Ks0AL1E4jC7IV9EIqrAVye8LkVNdxrM_DoMm3c_AHeb33kkQVKpbgeKBvALsB8ny3_cEDQ";

    private JwtTokenUtil util = new JwtTokenUtil();

    @Before
    public void setUp() throws Exception {
        setField(util, "secret", "zuocisecret");
        setField(util, "expiration", 604800L);
    }

    @Test
    public void shouldGetUsernameFromTokenGivenTokenGivenValidToken() {
        String username = util.getUsernameFromToken(validToken);
        Assert.assertEquals(username, "user");
    }

    @Test
    public void shouldGetIssuedAtDateFromTokenGivenValidToken() {
        Date date = util.getIssuedAtDateFromToken(validToken);
        Assert.assertNotNull(date);
    }

    @Test
    public void shouldGetExpirationDateFromTokenGivenValidToken() {
        Date expirationDate = util.getExpirationDateFromToken(validToken);
        Assert.assertNotNull(expirationDate);
    }

    @Test
    public void shouldGetAudienceFromTokenGivenValidToken() {
        String audience = util.getAudienceFromToken(validToken);
        Assert.assertNull(audience);
    }

    @Test
    public void shouldGetClaimFromTokenGivenValidToken() {
        String claim = util.getClaimFromToken(validToken, Claims::getSubject);
        Assert.assertEquals(claim, "user");
    }

    @Test
    public void shouldGenerateTokenGivenUserDetailsAndPlatform() {
        UserDetails user = new JwtUser(1L, "user", "", "password", null, true, new Date());
        String token = util.generateToken(user, "ios");
        Assert.assertNotNull(token);
        Assert.assertThat(token.length(), greaterThan(5));
        Assert.assertThat(token.split("\\.").length, is(3));
    }

    @Test
    public void shouldReturnFalseWhenCanTokenBeRefreshed() {
        Boolean canTokenBeRefreshed = util.canTokenBeRefreshed(validToken, new Date());
        Assert.assertFalse(canTokenBeRefreshed);
    }

    @Test
    public void shouldCanTokenBeRefreshed() {
        Boolean canTokenBeRefreshed = util.canTokenBeRefreshed(validToken, getValidLastResetPasswordDate());
        Assert.assertTrue(canTokenBeRefreshed);
    }

    @Test
    public void shouldRefreshTokenGivenValidToken() {
        String token = util.refreshToken(validToken);
        Assert.assertNotNull(token);
        Assert.assertThat(token.length(), greaterThan(5));
        Assert.assertThat(token.split("\\.").length, is(3));
    }

    @Test
    public void shouldNotValidateTokenWhenLastResetPasswordDateIsLater() {
        UserDetails user = new JwtUser(1L, "user", "", "password", null, true, new Date());
        Boolean isValid = util.validateToken(validToken, user);
        Assert.assertFalse(isValid);
    }

    @Test
    public void shouldNotValidateTokenWhenUsernameIsWrong() {
        Date date = getValidLastResetPasswordDate();
        UserDetails user = new JwtUser(1L, "wrong", "", "password", null, true, date);
        Boolean isValid = util.validateToken(validToken, user);
        Assert.assertFalse(isValid);
    }

    @Test
    public void shouldValidateToken() {
        Date date = getValidLastResetPasswordDate();
        UserDetails user = new JwtUser(1L, "user", "", "password", null, true, date);
        Boolean isValid = util.validateToken(validToken, user);
        Assert.assertTrue(isValid);
    }

    @Test(expected = ExpiredJwtException.class)
    public void shouldThrowExpiredExceptionWhenGetUsernameFromTokenGivenTokenGivenExpiredToken() {
        util.getUsernameFromToken(expiredtoken);
    }

    @Test(expected = ExpiredJwtException.class)
    public void shouldThrowExpiredExceptionWhenGetIssuedAtDateFromTokenGivenExpiredToken() {
        util.getIssuedAtDateFromToken(expiredtoken);
    }

    @Test(expected = ExpiredJwtException.class)
    public void shouldThrowExpiredExceptionWhenGetExpirationDateFromTokenGivenExpiredToken() {
        util.getExpirationDateFromToken(expiredtoken);
    }

    private Date getValidLastResetPasswordDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 1, 1);
        return calendar.getTime();
    }
}