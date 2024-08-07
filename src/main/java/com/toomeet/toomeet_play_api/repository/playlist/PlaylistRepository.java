package com.toomeet.toomeet_play_api.repository.playlist;

import com.toomeet.toomeet_play_api.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {
    boolean existsByName(String name);

    @Query("select count(v) from Playlist l join l.videos v")
    Integer countVideoByPlaylistId(String playlistId);
}
