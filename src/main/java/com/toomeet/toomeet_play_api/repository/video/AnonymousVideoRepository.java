package com.toomeet.toomeet_play_api.repository.video;

import com.toomeet.toomeet_play_api.dto.video.VideoDetailDto;
import com.toomeet.toomeet_play_api.dto.video.VideoNewsfeedDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnonymousVideoRepository extends VideoRepository {
    @Query("select " +
            "new com.toomeet.toomeet_play_api.dto.video.VideoNewsfeedDto(" +
            "v.id, " +
            "v.title, " +
            "c, " +
            "v.thumbnail, " +
            "count(distinct view.id), " +
            "count(distinct video_like.id), " +
            "v.createdAt, " +
            "v.updatedAt )" +
            " from Video v " +
            "join v.channel c " +
            "left join v.viewers view " +
            "left join v.likes video_like " +
            "where v.visibility = 'PUBLIC' " +
            "group by v, c"
    )
    Page<VideoNewsfeedDto> getNewsfeeds(Pageable pageable);


    @Query("select " +
            "new com.toomeet.toomeet_play_api.dto.video.VideoDetailDto ( " +
            "v.id,  " +
            "v.channel.id," +
            "v.title, " +
            "v.description, " +
            "v.allowedComment, " +
            "v.forKid, " +
            "count(distinct video_like.id), " +
            "count(distinct video_dislike.id), " +
            "count(distinct  comment.id), " +
            "count(distinct view.id), " +
            "false, " +
            "false, " +
            "false, " +
            "v.url, " +
            "v.createdAt, " +
            "v.updatedAt " +
            ") " +
            "from Video v " +
            "left join v.likes video_like " +
            "left join v.dislikes video_dislike " +
            "left join  v.viewers view " +
            "left join v.comments comment where v.id = :videoId group by v, v.channel.id")
    VideoDetailDto getVideoDetail(String videoId);
}
