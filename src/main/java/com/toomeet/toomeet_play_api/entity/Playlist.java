package com.toomeet.toomeet_play_api.entity;

import com.toomeet.toomeet_play_api.entity.video.Video;
import com.toomeet.toomeet_play_api.enums.Visibility;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Playlist extends Auditable {

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Visibility visibility = Visibility.PRIVATE;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private Channel channel;

    private String thumbnail;

    private int videoCount;

    @ManyToMany
    @JoinTable(
            name = "video_playlist",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private Set<Video> videos;

}
