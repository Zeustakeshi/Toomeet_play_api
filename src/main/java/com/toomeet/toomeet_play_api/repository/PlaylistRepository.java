package com.toomeet.toomeet_play_api.repository;

import com.toomeet.toomeet_play_api.dto.playlist.PlaylistBasicInfo;
import com.toomeet.toomeet_play_api.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    boolean existsByNameAndCreatedByUserId(String name, String userId);

    Playlist findByPlaylistId(String playlistId);

    List<Playlist> findAllByCreatedByUserId(String userId);

    @Query("SELECT " +
            "new com.toomeet.toomeet_play_api.dto.playlist.PlaylistBasicInfo(" +
            "p.playlistId, " +
            "p.name, " +
            "p.createdBy.userId" +
            ") FROM Playlist p WHERE p.playlistId = :playlistId")
    PlaylistBasicInfo getBasicInfoByPlaylistId(@Param("playlistId") String playlistId);

    @Query("SELECT COUNT(v) FROM Video v WHERE v.playlist.playlistId = :playlistId")
    Integer getPlaylistSize(@Param("playlistId") String playlistId);
}

