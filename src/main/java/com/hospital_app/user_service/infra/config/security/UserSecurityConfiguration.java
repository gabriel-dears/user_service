package com.hospital_app.user_service.infra.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class UserSecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> oAuth2Customizer) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oAuth2Customizer)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}

