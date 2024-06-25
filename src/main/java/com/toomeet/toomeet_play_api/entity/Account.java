package com.toomeet.toomeet_play_api.entity;

import com.toomeet.toomeet_play_api.enums.Authority;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = @Index(columnList = "accountId"))
public class Account extends BaseEntity implements UserDetails {

    @Setter(AccessLevel.PRIVATE)
    @Column(unique = true)
    @Builder.Default
    private String accountId = UUID.randomUUID().toString();

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(unique = true, updatable = false)
    private User user;

    @Column(name = "_user_id", unique = true, nullable = false)
    private String userId;

    private String image;

    private boolean isVerified;

    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.authorities
                .stream()
                .map(
                        authority -> new SimpleGrantedAuthority("ROLE_" + authority.name())
                ).toList();
    }


    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void addAllAuthority(Set<Authority> authorities) {
        this.authorities.addAll(authorities);
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