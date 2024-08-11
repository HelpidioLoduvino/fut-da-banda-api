package com.example.futdabandaapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Column(unique = true)
    private String email;
    private String password;
    private String confirmPassword;
    private String userRole;
    private String status;

    public User(Long id, String fullName, String email, String encodedPassword, String userRole, String status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = encodedPassword;
        this.userRole = userRole;
        this.status = status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        switch (this.userRole) {
            case "ADMIN" -> authorities.add(new SimpleGrantedAuthority("ADMIN"));
            case "SUB-CAPTAIN" -> authorities.add(new SimpleGrantedAuthority("SUB-CAPTAIN"));
            case "CAPTAIN" -> authorities.add(new SimpleGrantedAuthority("CAPTAIN"));
            case "PLAYER" -> authorities.add(new SimpleGrantedAuthority("PLAYER"));
            case "USER" -> authorities.add(new SimpleGrantedAuthority("USER"));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

