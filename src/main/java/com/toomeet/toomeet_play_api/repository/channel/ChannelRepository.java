package com.toomeet.toomeet_play_api.repository.channel;

import com.toomeet.toomeet_play_api.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {
    Channel findByAccountId(String accountId);

    @Query("select count(u) from Channel c left join c.subscribers u where c.id = :channelId")
    Long countSubscriber(String channelId);
}
