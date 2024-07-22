package com.toomeet.toomeet_play_api.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.toomeet.toomeet_play_api.entity.video.Video;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table(indexes = @Index(columnList = "name"))
public class Channel extends BaseEntity {

    @OneToOne(mappedBy = "channel", fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Account account;

    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String avatar;

    @ManyToMany
    @JoinTable(
            name = "channel_subscriber",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id"),
            indexes = @Index(columnList = "channel_id")
    )
    private Set<User> subscribers;

    @ManyToMany
    @JoinTable(
            name = "channel_member",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"),
            indexes = @Index(columnList = "channel_id")
    )
    private Set<User> members;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "channel")
    private Set<Video> videos;

}
