package com.toomeet.toomeet_play_api.entity;

import com.toomeet.toomeet_play_api.enums.Language;
import com.toomeet.toomeet_play_api.enums.VideoStatus;
import com.toomeet.toomeet_play_api.enums.Visibility;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Video extends Auditable {
    @Column(nullable = false, unique = true)
    @Builder.Default
    private String videoId = UUID.randomUUID().toString();

    @Column(nullable = false, unique = true)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private String thumbnail;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private VideoStatus status = VideoStatus.PROCESSING;

    private String url;

    private Long width;

    private Long height;

    private String format;

    @ManyToOne()
    private Channel channel;

    @ManyToMany()
    private List<VideoCategory> categories;

    @ElementCollection(targetClass = String.class)
    @Column(nullable = false)
    private List<String> tags;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Visibility visibility = Visibility.PRIVATE;

    @Column(nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Language languages = Language.VI;

    private LocalDateTime recordingDate;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;
}
