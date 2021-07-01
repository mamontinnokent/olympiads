package com.olympiads.security;

import com.olympiads.entity.enums.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Getter
public class JwtUser implements UserDetails {

    private final Long id;
    private final Role role;
    private final String name;
    private final String email;
    private final String surname;
    private final String password;
    private final String[] lessons;
    private final Collection<? extends GrantedAuthority> authorities;


    public JwtUser(
            Long id,
            Role role,
            String name,
            String email,
            String surname,
            String password,
            String[] lessons,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.email = email;
        this.surname = surname;
        this.lessons = lessons;
        this.password = password;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
