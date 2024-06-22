package com.toomeet.toomeet_play_api.entity;

import com.toomeet.toomeet_play_api.enums.Visibility;
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
public class Playlist extends Auditable {
    @Builder.Default
    @Setter(AccessLevel.PRIVATE)
    @Column(nullable = false, unique = true)
    private String playListId = UUID.randomUUID().toString();

    private String thumbnail;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Visibility visibility = Visibility.PRIVATE;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Video> videos;

    @Builder.Default
    private Integer videoCount = 0;
}
