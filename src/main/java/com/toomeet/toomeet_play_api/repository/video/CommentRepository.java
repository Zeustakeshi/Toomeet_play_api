package com.toomeet.toomeet_play_api.repository.video;

import com.toomeet.toomeet_play_api.entity.video.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query("select  c from Comment c " +
            "where c.video.id = :videoId and (:parentId is null  and c.parent.id is null or " +
            "c.parent.id = :parentId" +
            ")")
    Page<Comment> getAllByVideoIdAndParentId(String videoId, String parentId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Comment c set c.replyCount = (select count(r) from c.replies r) where c.id = :commentId")
    void updateReplyCount(String commentId);

    @Transactional
    @Modifying
    @Query("delete from Comment c where  c.id = :commentId or c.parent.id = :commentId")
    void deleteComment(String commentId);

    @Query("select case when count (c) > 0 then true else false end from Comment c where c.id = :commentId and c.user.id = :userId ")
    boolean isCommentOwner(String commentId, String userId);

    @Query("select " +
            "case " +
            "when count(user_liked) > 0 then true " +
            "else false " +
            "end " +
            "from Comment c join c.likes user_liked " +
            "where c.id = :commentId and user_liked.id = :userId")
    boolean isUserLikedComment(String userId, String commentId);

    @Query("select " +
            "case " +
            "when count(user_disliked) > 0 then true " +
            "else false " +
            "end " +
            "from Comment c join c.dislikes user_disliked " +
            "where c.id = :commentId and user_disliked.id = :userId")
    boolean isUserDislikedComment(String userId, String commentId);
}
