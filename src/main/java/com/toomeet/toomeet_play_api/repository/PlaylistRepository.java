package com.toomeet.toomeet_play_api.repository;

import com.toomeet.toomeet_play_api.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    boolean existsByNameAndCreatedByUserId(String name, String userId);

}
