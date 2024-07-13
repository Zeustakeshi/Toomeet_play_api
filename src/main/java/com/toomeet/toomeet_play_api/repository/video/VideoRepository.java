package com.toomeet.toomeet_play_api.repository.video;

import com.toomeet.toomeet_play_api.entity.video.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

    @Query(value = "select count(u) from Video v join v.viewers u where v.id = :videoId")
    Integer countVideoView(String videoId);

    @Query(value = "select count(u) from Video v join v.comments u where v.id = :videoId")
    Integer countVideoComment(String videoId);


    @Query(value = "select count(u) from Video v join v.likes u where v.id = :videoId")
    Integer countVideoLike(String videoId);

    @Query(value = "select count(u) from Video v join v.dislikes u where v.id = :videoId")
    Integer countVideoDislike(String videoId);
}
