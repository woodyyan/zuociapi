package com.easystudio.api.zuoci.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

public class JwtUser implements UserDetails {

    private final Long id;
    private final String appKey;
    private final String appSecret;
    private final String description;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final Date lastSecretResetDate;

    public JwtUser(
            Long id,
            String appKey,
            String description,
            String appSecret, Collection<? extends GrantedAuthority> authorities,
            boolean enabled,
            Date lastSecretResetDate
    ) {
        this.id = id;
        this.appKey = appKey;
        this.description = description;
        this.appSecret = appSecret;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastSecretResetDate = lastSecretResetDate;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return appKey;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getDescription() {
        return description;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return appSecret;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public Date getLastSecretResetDate() {
        return lastSecretResetDate;
    }
}