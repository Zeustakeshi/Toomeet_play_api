package com.toomeet.toomeet_play_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Channel extends Auditable {

    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    @Column(nullable = false, unique = true)
    private String channelId = UUID.randomUUID().toString();
    private String name;

    @OneToOne()
    private Account account;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String background;

    private String avatar;

    @Builder.Default
    private Long totalWatchTime = 0L;

    @Builder.Default
    private Long viewCount = 0L;

    @ManyToMany
    private List<User> members;

    @ManyToMany
    private List<User> subscribers;

    @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Video> videos;

}
