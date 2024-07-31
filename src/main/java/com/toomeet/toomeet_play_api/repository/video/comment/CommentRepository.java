package com.toomeet.toomeet_play_api.repository.video.comment;

import com.toomeet.toomeet_play_api.dto.video.comment.CommentDetailDto;
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

    @Query("select new com.toomeet.toomeet_play_api.dto.video.comment.CommentDetailDto(" +
            "c.id, " +
            "c.content, " +
            "count (distinct reply.id), " +
            "count(distinct comment_like.id), " +
            "count(distinct comment_dislike.id), " +
            "c.user, " +
            "case when count (distinct user_liked.id) > 0 then true else false end, " +
            "case when count (distinct user_disliked.id) > 0 then true else false end, " +
            "c.createdAt, " +
            "c.updatedAt " +
            ")" +
            " from Comment c " +
            "left join c.replies reply " +
            "left join c.likes comment_like " +
            "left join c.dislikes comment_dislike " +
            "left join c.likes user_liked on user_liked.id = :userId " +
            "left join c.dislikes user_disliked on user_disliked.id = :userId " +
            "where c.video.id = :videoId and (:parentId is null and c.parent.id is null or c.parent.id = :parentId)" +
            "group by c, c.user")
    Page<CommentDetailDto> getAllByVideoIdAndParentId(String videoId, String parentId, String userId, Pageable pageable);


//    @Query("select new com.toomeet.toomeet_play_api.dto.video.comment.CommentDetailDto(" +
//            "c.id, " +
//            "c.content, " +
//            "count (distinct reply.id), " +
//            "count(distinct comment_like.id), " +
//            "count(distinct comment_dislike.id), " +
//            "c.user, " +
//            "case when count (distinct user_liked.id) > 0 then true else false end, " +
//            "case when count (distinct user_disliked.id) > 0 then true else false end, " +
//            "c.createdAt, " +
//            "c.updatedAt " +
//            ")" +
//            " from Comment c " +
//            "left join c.replies reply " +
//            "left join c.likes comment_like " +
//            "left join c.dislikes comment_dislike " +
//            "left join c.likes user_liked on user_liked.id = :userId " +
//            "left join c.dislikes user_disliked on user_disliked.id = :userId " +
//            "where c.id = :commentId " +
//            "group by c")
//    CommentDetailDto getCommentDetailWidthInteraction(String commentId, String userId);


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
