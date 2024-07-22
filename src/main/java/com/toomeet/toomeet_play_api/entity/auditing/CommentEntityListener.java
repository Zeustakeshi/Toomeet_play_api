package com.toomeet.toomeet_play_api.entity.auditing;

import com.toomeet.toomeet_play_api.entity.video.Comment;
import jakarta.persistence.PrePersist;

public class CommentEntityListener {
    @PrePersist
    public void prePersist(Comment comment) {
        if (comment.getParent() != null) {
            comment.getParent().incrementReplyCount();
        }
    }
}
