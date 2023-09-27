package com.example.salineroyaleacademy.security.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.salineroyaleacademy.user.Permission.ADMIN_CREATE;
import static com.example.salineroyaleacademy.user.Permission.ADMIN_DELETE;
import static com.example.salineroyaleacademy.user.Permission.ADMIN_READ;
import static com.example.salineroyaleacademy.user.Permission.ADMIN_UPDATE;
import static com.example.salineroyaleacademy.user.Permission.MANAGER_CREATE;
import static com.example.salineroyaleacademy.user.Permission.MANAGER_DELETE;
import static com.example.salineroyaleacademy.user.Permission.MANAGER_READ;
import static com.example.salineroyaleacademy.user.Permission.MANAGER_UPDATE;
import static com.example.salineroyaleacademy.user.Role.ADMIN;
import static com.example.salineroyaleacademy.user.Role.MANAGER;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

  @Autowired
  private JwtAuthenticationFilter jwtAuthFilter;

  @Autowired
  private  AuthenticationProvider authenticationProvider;

  @Autowired
  private  AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth.requestMatchers(
            "/api/**",
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html")
            .permitAll()
            .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
            .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
            .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
            .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
            .requestMatchers(DELETE, "/api/v1/management/**")
            .hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
            .anyRequest().authenticated());

    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

    return http.build();
  }
}
