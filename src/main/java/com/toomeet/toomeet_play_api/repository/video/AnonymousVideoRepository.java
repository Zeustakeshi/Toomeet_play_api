package com.toomeet.toomeet_play_api.repository.video;

import com.toomeet.toomeet_play_api.entity.video.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnonymousVideoRepository extends JpaRepository<Video, String> {
    @Query("select v from Video v join fetch v.channel where v.visibility = 'PUBLIC'")
    Page<Video> getAllAnonymous(Pageable pageable);

}
