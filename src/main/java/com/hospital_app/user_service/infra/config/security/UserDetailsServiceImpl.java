package com.hospital_app.user_service.infra.config.security;

import com.hospital_app.user_service.application.exception.InvalidCredentialsException;
import com.hospital_app.user_service.application.port.out.user.CustomUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomUserRepository customUserRepository;

    public UserDetailsServiceImpl(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = customUserRepository.findByUsername(username).orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));
        return new UserDetailsAdapter(user);
    }
}
