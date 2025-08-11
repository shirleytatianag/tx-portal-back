package com.example.portal.transactional_portal.auth.repository;

import com.example.portal.transactional_portal.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User getUserByUsername(String username);
}
