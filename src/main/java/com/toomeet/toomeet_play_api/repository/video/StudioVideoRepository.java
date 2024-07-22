package com.toomeet.toomeet_play_api.repository.video;

import com.toomeet.toomeet_play_api.dto.response.video.VideoSummaryResponse;
import com.toomeet.toomeet_play_api.entity.video.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudioVideoRepository extends JpaRepository<Video, String> {
    @Query("select case when v.channel.id = :channelId then true else false end from Video v where v.id = :videoId")
    boolean isOwner(String channelId, String videoId);

    @Query("select v from Video v where v.id = :videoId and v.channel.id = :channelId")
    Optional<Video> getVideoByIdAndChannelId(String videoId, String channelId);

    @Query("select v from Video v left join v.viewers viewer where v.channel.id = :channelId group by v order by count(viewer) desc")
    Page<Video> getTopVideoByChannelId(String channelId, Pageable pageable);


    @Query("select new com.toomeet.toomeet_play_api.dto.response.video.VideoSummaryResponse(" +
            "v.id, " +
            "v.title, " +
            "v.description, " +
            "v.thumbnail, " +
            "count(distinct viewer.id), " +
            "count (distinct comment.id), " +
            "count(distinct video_like.id), " +
            "count(distinct video_dislike.id), " +
            "v.visibility, " +
            "v.createdAt, " +
            "v.updatedAt)" +
            "from Video v " +
            "left join v.viewers viewer " +
            "left join v.comments comment " +
            "left join v.likes video_like " +
            "left join v.dislikes video_dislike " +
            "where v.channel.id = :channelId " +
            "group by v"
    )
    Page<VideoSummaryResponse> getAllSummaryByChannelId(String channelId, Pageable pageable);

}
