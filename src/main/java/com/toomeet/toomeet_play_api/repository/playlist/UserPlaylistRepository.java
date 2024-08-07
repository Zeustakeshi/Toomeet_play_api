/*
 *  UserPlaylistRepository
 *  @author: Minhhieuano
 *  @created 8/7/2024 1:31 PM
 * */

package com.toomeet.toomeet_play_api.repository.playlist;

import com.toomeet.toomeet_play_api.dto.playlist.PlaylistTotalVideoDto;
import com.toomeet.toomeet_play_api.entity.Playlist;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlaylistRepository extends PlaylistRepository {
    @Query("select (count(p) > 0) from Playlist p where p.id = :playlistId and p.channel.id = :channelId")
    boolean isPlaylistOwner(String playlistId, String channelId);

    @Query("select p from Playlist p where p.id = :playlistId and p.channel.id = :channelId")
    Optional<Playlist> getPlaylistByIdAndChannelId(String playlistId, String channelId);

    @Query("select " + "new com.toomeet.toomeet_play_api.dto.playlist.PlaylistTotalVideoDto("
            + "p.id, "
            + "p.name, "
            + "p.description, "
            + "p.thumbnail, "
            + "count (distinct v), "
            + "p.channel, "
            + "p.visibility, "
            + "p.createdAt, "
            + "p.updatedAt"
            + ")  from Playlist p "
            + "left join p.videos v " + "where p.channel.id = :channelId "
            + "group by p, p.channel")
    Page<PlaylistTotalVideoDto> findAllByChannelId(String channelId, Pageable pageable);
}
