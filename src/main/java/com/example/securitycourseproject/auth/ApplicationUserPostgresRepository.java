package com.example.securitycourseproject.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserPostgresRepository
        extends JpaRepository<ApplicationUser, String> {
}
