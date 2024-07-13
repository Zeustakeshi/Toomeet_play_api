package com.toomeet.toomeet_play_api.repository.video;

import com.toomeet.toomeet_play_api.entity.video.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
}
