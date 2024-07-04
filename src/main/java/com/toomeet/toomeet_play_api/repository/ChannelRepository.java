package com.toomeet.toomeet_play_api.repository;

import com.toomeet.toomeet_play_api.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {
    Channel findAllByAccountId(String accountId);

    @Query("select count(m) from Channel c join c.members m where c.id = :channelId")
    Integer countMember(String channelId);

    @Query("select count (s) from Channel c join c.subscribers s where c.id = :channelId")
    Integer countSubscriber(String channelId);

    @Query("select count (v) from Channel c join c.videos v where c.id = :channelId")
    Integer countVideo(String channelId);
}
