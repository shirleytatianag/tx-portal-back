package com.example.portal.transactional_portal.auth.service;

import com.example.portal.transactional_portal.auth.dto.AuthRequest;
import com.example.portal.transactional_portal.auth.dto.AuthResponse;
import com.example.portal.transactional_portal.user.entity.User;

public interface AuthService {

    AuthResponse login(AuthRequest request);

    String generateToken(String userName);

    User getUserAuthenticated();
}
