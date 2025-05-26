package com.co.quality.clothing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.GET, "/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/**").authenticated()
                    .anyRequest().permitAll()
                                  )
            .httpBasic(httpBasic -> {});

        return http.build();
    }
}