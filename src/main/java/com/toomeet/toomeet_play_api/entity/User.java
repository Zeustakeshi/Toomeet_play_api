package com.toomeet.toomeet_play_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User extends BaseEntity {

    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    @Column(nullable = false, unique = true)
    private String userId = UUID.randomUUID().toString();

    private boolean isVerified;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Account account;

    @Column(name = "_account_id", unique = true, nullable = false)
    private String accountId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "subscribers", fetch = FetchType.LAZY)
    private List<Channel> subscribedChannels;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private List<Channel> memberChannels;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdBy")
    private List<Playlist> playlists;
}
