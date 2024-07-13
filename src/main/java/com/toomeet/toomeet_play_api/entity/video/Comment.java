package com.toomeet.toomeet_play_api.entity.video;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toomeet.toomeet_play_api.entity.Auditable;
import com.toomeet.toomeet_play_api.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(indexes = @Index(columnList = "video_id"))
public class Comment extends Auditable {

    @ManyToOne()
    private User user;

    @ManyToOne()
    private Video video;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Comment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<Comment> replies;

    @ManyToMany
    @JoinTable(name = "liked_comment",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes;


    @ManyToMany
    @JoinTable(name = "disliked_comment",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> dislikes;

}
