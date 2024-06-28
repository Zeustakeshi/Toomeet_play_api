package com.toomeet.toomeet_play_api.repository;

import com.toomeet.toomeet_play_api.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query("select case when COUNT(p) > 0 then true else false end from Playlist p where p.createdBy.userId = :userId and p.name = :name")
    boolean existsByNameAndUserId(@Param("name") String name, @Param("userId") String userId);

    Playlist findByPlaylistId(String playlistId);

    @Query("select p from Playlist  p where p.createdBy.userId = :userId")
    List<Playlist> findAllByUserId(@Param("userId") String userId);

    @Query("SELECT COUNT(v) FROM Video v WHERE v.playlist.playlistId = :playlistId")
    Integer getPlaylistSize(@Param("playlistId") String playlistId);


    @Query("select case when p.createdBy.userId = :userId then true else false end from Playlist p where p.playlistId = :playlistId")
    boolean isPlaylistOwner(
            @Param("playlistId") String playlistId,
            @Param("userId") String userId
    );
}

