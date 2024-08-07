package com.toomeet.toomeet_play_api.entity;

import com.toomeet.toomeet_play_api.entity.auditing.AccountEntityListener;
import com.toomeet.toomeet_play_api.enums.Role;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Table(name = "Account", indexes = @Index(columnList = "email"))
@EqualsAndHashCode(
        callSuper = true,
        exclude = {"user", "channel"})
@EntityListeners(AccountEntityListener.class)
public class Account extends BaseEntity implements UserDetails {

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    @Column(name = "_user_id", unique = true, nullable = false, updatable = false)
    private String userId;

    @Column(name = "_channel_id", unique = true, nullable = false, updatable = false)
    private String channelId;

    private String image;

    private boolean isVerified;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Account_authority", joinColumns = @JoinColumn(name = "account_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Role> authorities = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true, updatable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true, updatable = false)
    private Channel channel;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.authorities.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();
    }

    public void setUser(User user) {
        this.user = user;
        this.userId = user.getId();
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
        this.channelId = channel.getId();
    }

    public void addAuthority(Role role) {
        this.authorities.add(role);
    }

    public void addAllAuthority(Set<Role> authorities) {
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
