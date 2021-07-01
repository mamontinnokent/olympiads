package com.olympiads.service;

import com.olympiads.entity.User;
import com.olympiads.repository.UserRepository;
import com.olympiads.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static JwtUser build(User user) {
        List<GrantedAuthority> authorities = Stream.of(user.getRole())
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new JwtUser(
                user.getId(),
                user.getRole(),
                user.getName(),
                user.getEmail(),
                user.getSurname(),
                user.getPassword(),
                user.getLessons().split(" "),
                authorities
        );
    }

    @Override
    public JwtUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email " + email));

        return build(user);
    }

    public JwtUser loadById(Long id) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + id));

        return build(user);
    }
}