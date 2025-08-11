package com.example.portal.transactional_portal.auth.security.service;

import com.example.portal.transactional_portal.auth.repository.UserRepository;
import com.example.portal.transactional_portal.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetail implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetail(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with userName: " + username);
        }
        return UserDetailsImpl.build(user);
    }
}
