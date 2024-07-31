/*
 *  StudioChannelRepository
 *  @author: Minhhieuano
 *  @created 7/31/2024 10:55 PM
 * */


package com.toomeet.toomeet_play_api.repository.channel;

import com.toomeet.toomeet_play_api.dto.response.channel.ChannelAnalyticsResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioChannelRepository extends ChannelRepository {
    @Query("select " +
            "new com.toomeet.toomeet_play_api.dto.response.channel.ChannelAnalyticsResponse(" +
            "count(distinct s.id) ,count( distinct  m.id), count(distinct v.id)" +
            ") " +
            "from Channel c " +
            "left join c.videos v " +
            "left join c.subscribers s " +
            "left join c.members m " +
            "where c.id = :channelId"
    )
    ChannelAnalyticsResponse getChannelAnalytics(String channelId);
}
