package com.toomeet.toomeet_play_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.toomeet.toomeet_play_api.enums.Authority;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {

    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    @Column(nullable = false, unique = true)
    private String userId = UUID.randomUUID().toString();

    private String firstName;
    private String lastName;
    private String fullName;
    private String image;
    private String password;


    @Column(unique = true, nullable = false)
    private String email;

    private boolean isVerified;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "subscribers")
    private List<Channel> subscribedChannels;

    @ManyToMany(mappedBy = "members")
    private List<Channel> memberChannels;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private Channel channel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdBy")
    private List<Playlist> playlists;

    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void addAllAuthority(Set<Authority> authorities) {
        this.authorities.addAll(authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.authorities
                .stream()
                .map(
                        authority -> new SimpleGrantedAuthority("ROLE_" + authority.name())
                ).toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return isVerified;
    }
}
