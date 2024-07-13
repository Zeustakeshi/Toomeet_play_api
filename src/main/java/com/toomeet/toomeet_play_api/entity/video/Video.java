package com.toomeet.toomeet_play_api.entity.video;

import com.toomeet.toomeet_play_api.entity.Auditable;
import com.toomeet.toomeet_play_api.entity.Channel;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.enums.Language;
import com.toomeet.toomeet_play_api.enums.ResourceUploadStatus;
import com.toomeet.toomeet_play_api.enums.Visibility;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Video extends Auditable {

    @Builder.Default
    private boolean allowedComment = true;

    @Builder.Default
    private boolean forKid = true;

    @Length(max = 3000)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String url;

    private String thumbnail;

    @Builder.Default
    private boolean canPublic = false;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Language language = Language.VI;

    private LocalDateTime recordDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Visibility visibility = Visibility.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    private Channel channel;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ResourceUploadStatus uploadStatus = ResourceUploadStatus.PROCESSING;

    private Long width;

    private Long height;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tag> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "video_view",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            indexes = @Index(columnList = "video_id")
    )
    private Set<User> viewers;

    @ManyToMany
    @JoinTable(name = "liked_video",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes;

    @ManyToMany
    @JoinTable(name = "disliked_video",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> dislikes;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY)
    private Set<Comment> comments;

}
