package com.example.securitycourseproject.security;


import com.example.securitycourseproject.auth.ApplicationUser;
import com.example.securitycourseproject.auth.ApplicationUserPostgresRepository;
import com.example.securitycourseproject.auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import java.util.concurrent.TimeUnit;

import static com.example.securitycourseproject.security.ApplicationUserRole.*;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final ApplicationUserPostgresRepository applicationUserPostgresRepository;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
                                     ApplicationUserService applicationUserService,
                                     ApplicationUserPostgresRepository applicationUserPostgresRepository) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.applicationUserPostgresRepository = applicationUserPostgresRepository;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/courses", true)
                    .passwordParameter("password")
                    .usernameParameter("username")
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) // defaults to 2 weeks
                    .key("somethingverysecure")
                    .userDetailsService(applicationUserService)
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    // should use HTTP.POST request to log out, and disable GET request logout when csrf is enabled.
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("remember-me", "JSESSIONID")
                    .logoutSuccessUrl("/login");
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

            ApplicationUser anna = new ApplicationUser("anna",
                    passwordEncoder.encode("password"),
                    STUDENT.getGrantedAuthorities(),
                    true,
                    true,
                    true,
                    true
            );
            ApplicationUser linda = new ApplicationUser("linda",
                    passwordEncoder.encode("password2"),
                    ADMIN.getGrantedAuthorities(),
                    true,
                    true,
                    true,
                    true
            );
            ApplicationUser tom = new ApplicationUser("tom",
                    passwordEncoder.encode("password2"),
                    ADMINTRAINEE.getGrantedAuthorities(),
                    true,
                    true,
                    true,
                    true
            );

            applicationUserPostgresRepository.save(anna);
            applicationUserPostgresRepository.save(linda);
            applicationUserPostgresRepository.save(tom);
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }


}
