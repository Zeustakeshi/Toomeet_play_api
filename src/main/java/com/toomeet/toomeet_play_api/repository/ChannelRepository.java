package com.toomeet.toomeet_play_api.repository;

import com.toomeet.toomeet_play_api.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Channel findByChannelId(String channelId);

    Channel findByOwnerUserId(String ownerId);
}
