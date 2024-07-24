package com.toomeet.toomeet_play_api.entity.video;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toomeet.toomeet_play_api.entity.Auditable;
import com.toomeet.toomeet_play_api.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "comments", indexes = @Index(columnList = "video_id"))
public class Comment extends Auditable {

    @ManyToOne()
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Video video;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Comment parent;

    private boolean isReply;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private Set<Comment> replies = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "liked_comment",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private Set<User> likes = new HashSet<>();

    @ManyToMany
    @Builder.Default
    @JoinTable(name = "disliked_comment",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> dislikes = new HashSet<>();


    public void setParent(Comment parent) {
        this.parent = parent;
        this.isReply = true;
    }

}
