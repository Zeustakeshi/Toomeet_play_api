package com.toomeet.toomeet_play_api.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.toomeet.toomeet_play_api.entity.video.Video;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Users")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    @JoinColumn(unique = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Account account;

    @ManyToMany(mappedBy = "subscribers")
    private Set<Channel> subscribedChannels;


    @ManyToMany(mappedBy = "members")
    private Set<Channel> memberChannels;

    @ManyToMany
    @JoinTable(
            name = "watched_list",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id"),
            indexes = @Index(columnList = "user_id")
    )
    private Set<Video> watchedVideos;


}
