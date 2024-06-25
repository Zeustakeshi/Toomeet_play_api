package com.toomeet.toomeet_play_api.repository;

import com.toomeet.toomeet_play_api.dto.video.VideoBasicInfo;
import com.toomeet.toomeet_play_api.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findByVideoId(String videoId);

    @Query("select v from Video v where v.playlist.playlistId = :playlistId")
    Set<Video> findAllByPlaylistId(String playlistId);

    @Query("SELECT " +
            "new com.toomeet.toomeet_play_api.dto.video.VideoBasicInfo(v.videoId, v.title) " +
            "FROM Video v WHERE v.videoId = :videoId")
    VideoBasicInfo getVideoBasicInfoByVideoId(@Param("videoId") String videoId);
}
