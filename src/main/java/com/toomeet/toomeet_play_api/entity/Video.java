package com.toomeet.toomeet_play_api.entity;

import com.toomeet.toomeet_play_api.enums.Language;
import com.toomeet.toomeet_play_api.enums.Visibility;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Video extends Auditable {
    @Column(nullable = false, unique = true)
    private String videoId;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private String thumbnail;

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
    private Playlist playlist;
}
