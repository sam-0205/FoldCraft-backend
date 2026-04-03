package com.example.backend.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "origami_id")
    )
    private Set<Origami> favorites;

    @ManyToMany
    @JoinTable(
            name = "user_completed",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "origami_id")
    )
    private Set<Origami> completed;

    // ================= CONSTRUCTORS =================
    public User() {}

    public User(Long id, String username, String email, String password,
                String displayName, String avatarUrl, Role role,
                LocalDateTime createdAt, LocalDateTime updatedAt,
                Set<Origami> favorites, Set<Origami> completed) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.favorites = favorites;
        this.completed = completed;
    }

    // ================= GETTERS & SETTERS =================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Override
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Set<Origami> getFavorites() { return favorites; }
    public void setFavorites(Set<Origami> favorites) { this.favorites = favorites; }

    public Set<Origami> getCompleted() { return completed; }
    public void setCompleted(Set<Origami> completed) { this.completed = completed; }

    // ================= JPA HOOKS =================
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (role == null) role = Role.USER;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ================= SPRING SECURITY =================
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    // ================= ENUM =================
    public enum Role {
        USER, ADMIN
    }
}