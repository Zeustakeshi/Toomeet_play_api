package com.toomeet.toomeet_play_api.repository.video;

import com.toomeet.toomeet_play_api.dto.video.VideoPreviewDto;
import com.toomeet.toomeet_play_api.entity.video.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnonymousVideoRepository extends JpaRepository<Video, String> {
    @Query("select " +
            "new com.toomeet.toomeet_play_api.dto.video.VideoPreviewDto(" +
            "v.id, " +
            "v.title, " +
            "c, " +
            "v.thumbnail, " +
            "count(distinct view.id), " +
            "count(distinct video_like.id), " +
            "v.createdAt, " +
            "v.updatedAt )" +
            " from Video v " +
            "join v.channel c " +
            "left join v.viewers view " +
            "left join v.likes video_like " +
            "where v.visibility = 'PUBLIC' " +
            "group by v, c"
    )
    Page<VideoPreviewDto> getAll(Pageable pageable);

}
