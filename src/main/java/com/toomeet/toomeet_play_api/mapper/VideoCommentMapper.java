package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.video.comment.CommentReactionResponse;
import com.toomeet.toomeet_play_api.dto.response.video.comment.CommentResponse;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.entity.video.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface VideoCommentMapper {


    @Mapping(source = "likes", target = "like")
    @Mapping(source = "dislikes", target = "dislike")
    CommentReactionResponse toCommentReactionResponse(Comment comment);


    default int countUsers(Set<User> users) {
        return users != null ? users.size() : 0;
    }

    @Mapping(source = "comment.user.account.name", target = "owner.name")
    @Mapping(source = "comment.user.account.image", target = "owner.avatar")
    @Mapping(source = "comment.id", target = "id")
    @Mapping(source = "comment.createdAt", target = "createdAt")
    @Mapping(source = "comment.updatedAt", target = "updatedAt")
    @Mapping(source = "comment.likeCount", target = "totalLikes")
    @Mapping(source = "comment.dislikeCount", target = "totalDislikes")
    @Mapping(source = "comment.replyCount", target = "totalReplies")
    CommentResponse toCommentResponse(Comment comment, String userId);


}
