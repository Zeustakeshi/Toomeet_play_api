package com.toomeet.toomeet_play_api.repository;

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

    @Query("select v from Video  v where v.createdBy.userId = :userId")
    Set<Video> findAllByOwnerId(@Param("userId") String userId);

}
