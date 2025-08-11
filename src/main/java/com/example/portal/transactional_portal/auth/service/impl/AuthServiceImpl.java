package com.example.portal.transactional_portal.auth.service.impl;

import com.example.portal.transactional_portal.auth.dto.AuthRequest;
import com.example.portal.transactional_portal.auth.dto.AuthResponse;
import com.example.portal.transactional_portal.auth.exception.AuthFailedException;
import com.example.portal.transactional_portal.auth.repository.UserRepository;
import com.example.portal.transactional_portal.auth.security.jwt.JwtTokenProvider;
import com.example.portal.transactional_portal.auth.service.AuthService;
import com.example.portal.transactional_portal.common.enums.Messages;
import com.example.portal.transactional_portal.common.exception.NotFoundException;
import com.example.portal.transactional_portal.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(dontRollbackOn = {AuthFailedException.class})
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.getUserByUsername(request.getUsername().trim().toLowerCase(Locale.ROOT));

        if (user == null) {
            throw new NotFoundException(Messages.CREDENTIAL_INVALID.getMessage());
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new NotFoundException(Messages.CREDENTIAL_INVALID.getMessage());
        }

        return AuthResponse.create(generateToken(user.getUsername()));
    }

    @Override
    public String generateToken(String username) {
        return jwtTokenProvider.generateToken(username);
    }

    @Override
    public User getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userRepository.getUserByUsername(userDetails.getUsername());
        }
        return null;
    }
}
