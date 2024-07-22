package com.toomeet.toomeet_play_api.repository.video;

import com.toomeet.toomeet_play_api.dto.video.VideoDetailPublicDto;
import com.toomeet.toomeet_play_api.entity.video.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

    @Query("select case when v.visibility = 'PUBLIC' then true else false end from Video v where v.id = :videoId")
    boolean isPublicVideo(String videoId);

    @Query(value = "select count(u) from Video v join v.viewers u where v.id = :videoId")
    Integer countVideoView(String videoId);

    @Query(value = "select count(u) from Video v join v.comments u where v.id = :videoId")
    Integer countVideoComment(String videoId);

    @Query(value = "select count(u) from Video v join v.likes u where v.id = :videoId")
    Integer countVideoLike(String videoId);

    @Query(value = "select count(u) from Video v join v.dislikes u where v.id = :videoId")
    Integer countVideoDislike(String videoId);


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
