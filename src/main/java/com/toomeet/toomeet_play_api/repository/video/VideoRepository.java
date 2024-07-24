package com.toomeet.toomeet_play_api.repository.video;

import com.toomeet.toomeet_play_api.dto.response.video.VideoInteractionResponse;
import com.toomeet.toomeet_play_api.dto.video.VideoDetailPublicDto;
import com.toomeet.toomeet_play_api.entity.video.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

    @Query("select case when (v.visibility = 'PUBLIC' and  v.allowedComment = true) then true else false end from Video v where v.id = :videoId")
    boolean canCommentVideo(String videoId);


    @Query("select new com.toomeet.toomeet_play_api.dto.response.video.VideoInteractionResponse(" +
            "case when (count(distinct vl.id) > 0) then true else false end, " +
            "case when (count(distinct vd.id) > 0) then true else false end, " +
            "false) " +
            "from Video v " +
            "left join v.likes vl on vl.id = :userId " +
            "left join v.dislikes vd on vd.id = :userId " +
            "where v.id = :videoId")
    VideoInteractionResponse getVideoInteraction(String videoId, String userId);


    @Query("select " +
            "new com.toomeet.toomeet_play_api.dto.video.VideoDetailPublicDto(" +
            "v.id, " +
            "v.title, " +
            "v.description, " +
            "v.allowedComment, " +
            "v.forKid, " +
            "c, " +
            "count(distinct video_like.id), " +
            "count(distinct video_dislike.id), " +
            "count(distinct  comment.id), " +
            "count(distinct view.id), v.url, v.createdAt, v.updatedAt ) " +
            "from Video v " +
            "join v.channel c " +
            "left join v.likes video_like " +
            "left join v.dislikes video_dislike " +
            "left join  v.viewers view " +
            "left join v.comments comment where v.id = :videoId group by v, c")
    VideoDetailPublicDto getVideoDetailPublic(String videoId);


}
