package com.toomeet.toomeet_play_api.repository;

import com.toomeet.toomeet_play_api.dto.response.channel.ChannelAnalyticsResponse;
import com.toomeet.toomeet_play_api.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {
    Channel findAllByAccountId(String accountId);

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
