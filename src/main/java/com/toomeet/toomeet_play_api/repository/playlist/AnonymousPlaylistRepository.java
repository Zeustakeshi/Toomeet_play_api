/*
 *  AnonymousPlaylistRepository
 *  @author: Minhhieuano
 *  @created 8/7/2024 1:32 PM
 * */

package com.toomeet.toomeet_play_api.repository.playlist;

import com.toomeet.toomeet_play_api.dto.playlist.PlaylistTotalVideoDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnonymousPlaylistRepository extends PlaylistRepository {

    @Query("select new com.toomeet.toomeet_play_api.dto.playlist.PlaylistTotalVideoDto("
            + "p.id, "
            + "p.name, "
            + "p.description, "
            + "p.thumbnail, "
            + "count(distinct v), "
            + "p.channel, "
            + "p.visibility, "
            + "p.createdAt, "
            + "p.updatedAt"
            + ") from Playlist p " + "left join p.videos v "
            + "where p.id = :id and p.visibility = 'PUBLIC' "
            + "group by p, p.channel")
    Optional<PlaylistTotalVideoDto> findPlaylistById(String id);

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
            + "left join p.videos v " + "where p.channel.id = :channelId and p.visibility = 'PUBLIC' "
            + "group by p, p.channel")
    Page<PlaylistTotalVideoDto> findAllByChannelId(String channelId, Pageable pageable);
}
