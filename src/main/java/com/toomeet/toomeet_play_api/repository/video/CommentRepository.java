package com.toomeet.toomeet_play_api.repository.video;

import com.toomeet.toomeet_play_api.entity.video.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {


    Page<Comment> getAllByVideoId(String videoId, Pageable pageable);

    @Query("select count(l) from Comment c join c.likes l where c.id = :commentId")
    Integer countCommentLike(String commentId);

    @Query("select count(dislike) from Comment c join c.dislikes dislike where c.id = :commentId")
    Integer countCommentDislike(String commentId);

    @Query("select count(reply) from Comment c join c.replies reply where c.id = :commentId")
    Integer countCommentReplies(String commentId);

}
