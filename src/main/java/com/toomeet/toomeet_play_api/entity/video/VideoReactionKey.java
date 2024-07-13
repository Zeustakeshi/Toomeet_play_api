package com.toomeet.toomeet_play_api.entity.video;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class VideoReactionKey implements Serializable {

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "user_id")
    private String userId;

}
