package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.video.comment.CommentReactionCountResponse;
import com.toomeet.toomeet_play_api.dto.response.video.comment.CommentResponse;
import com.toomeet.toomeet_play_api.dto.video.comment.CommentDetailDto;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.entity.video.Comment;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class})
public interface VideoCommentMapper {

    @Mapping(source = "likes", target = "like")
    @Mapping(source = "dislikes", target = "dislike")
    CommentReactionCountResponse toCommentReactionResponse(Comment comment);

    default int countUsers(Set<User> users) {
        return users != null ? users.size() : 0;
    }

    @Mapping(source = "user", target = "owner")
    @Mapping(source = "comment.id", target = "id")
    @Mapping(source = "comment.createdAt", target = "createdAt")
    @Mapping(source = "comment.updatedAt", target = "updatedAt")
    CommentResponse toCommentResponse(Comment comment);

    CommentResponse toCommentResponse(CommentDetailDto comment);
}
