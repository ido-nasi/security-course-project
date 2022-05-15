package com.example.securitycourseproject.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.securitycourseproject.security.ApplicationUserRole.*;


@Repository("postgres")
public class PostgresApplicationUserDaoService implements ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserPostgresRepository applicationUserPostgresRepository;

    @Autowired
    public PostgresApplicationUserDaoService(PasswordEncoder passwordEncoder, ApplicationUserPostgresRepository postgresApplicationUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserPostgresRepository = postgresApplicationUserRepository;
    }

    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers().stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        return applicationUserPostgresRepository.findAll();
    }

}
