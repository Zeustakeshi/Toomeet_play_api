package com.toomeet.toomeet_play_api.repository.playlist;

import com.toomeet.toomeet_play_api.dto.playlist.PlaylistTotalVideoDto;
import com.toomeet.toomeet_play_api.entity.Playlist;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {
    boolean existsByName(String name);

    @Query("select (count(p) > 0) from Playlist p where p.id = :playlistId and p.channel.id = :channelId")
    boolean isPlaylistOwner(String playlistId, String channelId);

    @Query("select p from Playlist p where p.id = :playlistId and p.channel.id = :channelId")
    Optional<Playlist> getPlaylistByIdAndChannelId(String playlistId, String channelId);

    @Query("select count(v) from Playlist l join l.videos v")
    Integer countVideoByPlaylistId(String playlistId);

    @Query("select " + "new com.toomeet.toomeet_play_api.dto.playlist.PlaylistTotalVideoDto("
            + "p.id, "
            + "p.name, "
            + "p.description, "
            + "p.thumbnail, "
            + "count (v), "
            + "p.channel, "
            + "p.visibility, "
            + "p.createdAt, "
            + "p.updatedAt"
            + ")  from Playlist p "
            + "left join p.videos v where p.channel.id = :channelId "
            + "group by p.id, p.name, p.description, p.visibility, p.createdAt, p.updatedAt, p.thumbnail, p.channel")
    Page<PlaylistTotalVideoDto> findAllByChannelId(String channelId, Pageable pageable);
}
