package com.toomeet.toomeet_play_api.repository;

import com.toomeet.toomeet_play_api.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Channel findByChannelId(String channelId);

    @Query("select c from Channel  c join Account a on c.account.id = a.id where a.userId = :ownerId")
    Channel findByOwnerId(@Param("ownerId") String ownerId);

    @Query("select count (v.id) from Channel c join Video v on v.channel.id = c.id where c.channelId = :channelId")
    Long getVideoCountByChannelId(@Param("channelId") String channelId);
}
