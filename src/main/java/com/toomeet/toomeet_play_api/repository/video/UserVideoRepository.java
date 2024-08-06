/*
 *  UserVideoRepository
 *  @author: Minhhieuano
 *  @created 7/31/2024 8:05 PM
 * */

package com.toomeet.toomeet_play_api.repository.video;

import com.toomeet.toomeet_play_api.dto.response.video.VideoInteractionResponse;
import com.toomeet.toomeet_play_api.dto.video.VideoDetailDto;
import com.toomeet.toomeet_play_api.dto.video.VideoNewsfeedDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVideoRepository extends VideoRepository {

    @Query("select new com.toomeet.toomeet_play_api.dto.response.video.VideoInteractionResponse("
            + "case when (count(distinct vl.id) > 0) then true else false end, "
            + "case when (count(distinct vd.id) > 0) then true else false end, "
            + "false) "
            + "from Video v "
            + "left join v.likes vl on vl.id = :userId "
            + "left join v.dislikes vd on vd.id = :userId "
            + "where v.id = :videoId")
    VideoInteractionResponse getVideoInteraction(String videoId, String userId);

    @Query("select " + "new com.toomeet.toomeet_play_api.dto.video.VideoNewsfeedDto("
            + "v.id, "
            + "v.title, "
            + "c, "
            + "v.thumbnail, "
            + "count(distinct view.id), "
            + "count(distinct video_like.id), "
            + "v.createdAt, "
            + "v.updatedAt )"
            + " from Video v "
            + "join v.channel c "
            + "left join v.viewers view "
            + "left join v.likes video_like "
            + "where v.visibility = 'PUBLIC' "
            + "group by v, c")
    Page<VideoNewsfeedDto> getNewsfeeds(Pageable pageable);

    @Query("select " + "new com.toomeet.toomeet_play_api.dto.video.VideoDetailDto ( "
            + "v.id, "
            + "v.channel.id, "
            + "v.title, "
            + "v.description, "
            + "v.allowedComment, "
            + "v.forKid, "
            + "count(distinct video_like.id), "
            + "count(distinct video_dislike.id), "
            + "count(distinct  comment.id), "
            + "count(distinct view.id), "
            + "case when count (distinct user_like.id) > 0 then true else false end, "
            + "case when count (distinct user_dislike.id) > 0 then true else false end, "
            + "false, "
            + "v.url, "
            + "v.createdAt, "
            + "v.updatedAt "
            + ") "
            + "from Video v "
            + "left join v.likes video_like "
            + "left join v.likes user_like on user_like.id = :userId "
            + "left join v.dislikes video_dislike "
            + "left join v.dislikes user_dislike on user_dislike.id = :userId "
            + "left join  v.viewers view "
            + "left join v.comments comment where v.id = :videoId group by v, v.channel.id")
    VideoDetailDto getVideoDetail(String videoId, String userId);
}
