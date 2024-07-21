package com.example.futdabandaapi.entities;

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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at",
            nullable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    public User(Long id, String fullName, String email, String encodedPassword, String userRole, Date createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = encodedPassword;
        this.userRole = userRole;
        this.createdAt = createdAt;
    }

    public User(Long id, String fullName, String email, String password, String userRole) {
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, 1);
            createdAt = calendar.getTime();
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        switch (this.userRole) {
            case "ADMIN" -> authorities.add(new SimpleGrantedAuthority("ADMIN"));
            case "SUB-CAPTAIN" -> authorities.add(new SimpleGrantedAuthority("SUB-CAPTAIN"));
            case "CAPTAIN" -> authorities.add(new SimpleGrantedAuthority("CAPTAIN"));
            case "PLAYER" -> authorities.add(new SimpleGrantedAuthority("PLAYER"));
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

