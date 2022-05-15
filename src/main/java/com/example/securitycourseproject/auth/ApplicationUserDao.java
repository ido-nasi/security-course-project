package com.example.securitycourseproject.auth;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface ApplicationUserDao {
    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
