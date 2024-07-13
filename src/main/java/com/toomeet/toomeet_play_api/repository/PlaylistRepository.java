package com.toomeet.toomeet_play_api.repository;

import com.toomeet.toomeet_play_api.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {
    boolean existsByName(String name);

    @Query("select (count(p) > 0) from Playlist p where p.id = :playlistId and p.channel.id = :channelId")
    boolean isPlaylistOwner(String playlistId, String channelId);
}
