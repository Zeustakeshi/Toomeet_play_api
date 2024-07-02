package com.toomeet.toomeet_play_api.repository.video;

import com.toomeet.toomeet_play_api.entity.video.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {

    @Transactional
    @Modifying
    @Query("delete Tag t where t.video.id = :videoId")
    void deleteAllByVideoId(String videoId);

    @Query("select count(t) from Tag t where t.video.id = :videoId")
    Integer countByVideoId(String videoId);
}
