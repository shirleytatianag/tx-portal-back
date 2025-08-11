package com.example.portal.transactional_portal.auth.security.config;

import com.example.portal.transactional_portal.auth.security.jwt.JwtRequestFilter;
import com.example.portal.transactional_portal.auth.security.service.CustomUserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetail customUserDetail;
    private final JwtRequestFilter jwtRequestFilter;

    private final String[] ROUTES_ALLOWED_WITHOUT_AUTHENTICATION = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

    private final String[] ROUTES_POST_ALLOWED_WITHOUT_AUTHENTICATION = {
            "/auth/login",
    };

    public SecurityConfig(CustomUserDetail customUserDetail, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetail = customUserDetail;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfig corsConfig) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig.configurationSource()))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers(ROUTES_ALLOWED_WITHOUT_AUTHENTICATION).permitAll();
                    registry.requestMatchers(HttpMethod.POST, ROUTES_POST_ALLOWED_WITHOUT_AUTHENTICATION).permitAll();
                    registry.anyRequest().authenticated();
                }).exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedHandler((request, response, accessDeniedException) -> {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("message", "Acceso denegado");
                    ObjectMapper mapper = new ObjectMapper();
                    response.setStatus(403);
                    response.setHeader("Content-Type", "application/json");
                    mapper.writeValueAsString(map);
                    response.getWriter().write(mapper.writeValueAsString(map));
                }).authenticationEntryPoint((request, response, authException) -> {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("message", "Acceso no autorizado");
                    ObjectMapper mapper = new ObjectMapper();
                    response.setStatus(401);
                    response.setHeader("Content-Type", "application/json");
                    response.getWriter().write(mapper.writeValueAsString(map));
                }));
        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetail);
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return authenticationProvider;
    }

}
