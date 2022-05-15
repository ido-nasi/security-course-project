package com.example.securitycourseproject.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity(name = "ApplicationUser")
@Table(name = "ApplicationUser",
        uniqueConstraints = {
            @UniqueConstraint(name = "username_unique", columnNames = "username")
        }
)
public class ApplicationUser implements UserDetails {

    @Id
    @Column(name = "username")
    private final String username;

    @Column(name = "password")
    private final String password;

    @Column(name = "authorities")
    private final ArrayList<? extends GrantedAuthority> grantedAuthorities;

    @Column(name = "isAccountNonExpired",
            columnDefinition = "BOOLEAN"
    )
    private final boolean isAccountNonExpired;

    @Column(name = "isAccountNonLocked",
            columnDefinition = "BOOLEAN"
    )
    private final boolean isAccountNonLocked;

    @Column(name = "isCredentialsNonExpired",
            columnDefinition = "BOOLEAN"
    )
    private final boolean isCredentialsNonExpired;

    @Column(name = "isEnabled",
            columnDefinition = "BOOLEAN"
    )
    private final boolean isEnabled;


    public ApplicationUser(String username,
                           String password,
                           Set<? extends GrantedAuthority> grantedAuthorities,
                           boolean isAccountNonExpired,
                           boolean isAccountNonLocked,
                           boolean isCredentialsNonExpired,
                           boolean isEnabled) {
        this.grantedAuthorities = convertToArrayList(grantedAuthorities);
        this.password = password;
        this.username = username;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public ApplicationUser() {
        this.grantedAuthorities = null;
        this.password = null;
        this.username = null;
        this.isAccountNonExpired = false;
        this.isAccountNonLocked = false;
        this.isCredentialsNonExpired = false;
        this.isEnabled = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    private <T> ArrayList<T> convertToArrayList(Set<T> authorities) {
        ArrayList<T> s = new ArrayList<>();
        for (T auth:authorities)
            s.add(auth);

        return s;
    }

}
