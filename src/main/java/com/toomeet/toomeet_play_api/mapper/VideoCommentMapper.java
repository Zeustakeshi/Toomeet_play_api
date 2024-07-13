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

    @Mapping(source = "user.account.name", target = "owner.name")
    @Mapping(source = "user.account.image", target = "owner.avatar")
    CommentResponse toCommentResponse(Comment comment);


    @Mapping(source = "likes", target = "like")
    @Mapping(source = "dislikes", target = "dislike")
    CommentReactionResponse toCommentReactionResponse(Comment comment);


    default int countUsers(Set<User> users) {
        return users != null ? users.size() : 0;
    }

}
