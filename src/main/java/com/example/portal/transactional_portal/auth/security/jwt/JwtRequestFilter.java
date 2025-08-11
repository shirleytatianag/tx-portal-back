package com.example.portal.transactional_portal.auth.security.jwt;

import com.example.portal.transactional_portal.auth.security.service.CustomUserDetail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetail customUserDetail;

    public JwtRequestFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetail customUserDetail) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetail = customUserDetail;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if ((authHeader == null) || !(authHeader.startsWith("Bearer "))) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.split(" ")[1];

        if (!jwtTokenProvider.validJwt(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String username = jwtTokenProvider.extractorUserName(jwt);

            UserDetails userDetails = customUserDetail.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    List.of()
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (Exception e) {
            logger.error("Cannot set user authentication : {}", e);
        }

        filterChain.doFilter(request, response);
    }
}
