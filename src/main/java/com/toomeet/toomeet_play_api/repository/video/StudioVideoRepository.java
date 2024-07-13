package com.toomeet.toomeet_play_api.repository.video;

import com.toomeet.toomeet_play_api.entity.video.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioVideoRepository extends JpaRepository<Video, String> {
    @Query("select case when v.createdBy.id = :userId then true else false end from Video v where v.id = :videoId")
    boolean isOwner(String userId, String videoId);

    @Query("select v from Video v left join v.viewers viewer where v.channel.id = :channelId group by v order by count(viewer) desc")
    Page<Video> getTopVideoByChannelId(String channelId, Pageable pageable);

    Page<Video> getAllByChannelId(String channelId, Pageable pageable);

}
