package com.toomeet.toomeet_play_api.entity.video;

import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.enums.ReactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User_video_reaction")
public class VideoReaction {
    @EmbeddedId
    private VideoReactionKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User viewer;

    @ManyToOne
    @MapsId("videoId")
    @JoinColumn(name = "video_id")
    private Video video;

    @Enumerated(EnumType.STRING)
    private ReactionType type;
}
