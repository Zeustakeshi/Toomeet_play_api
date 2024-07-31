/*
 *  UserChannelRepository
 *  @author: Minhhieuano
 *  @created 8/1/2024 12:34 AM
 * */


package com.toomeet.toomeet_play_api.repository.channel;

import com.toomeet.toomeet_play_api.dto.response.channel.UserChannelBasicInfoResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChannelRepository extends ChannelRepository {
    @Query("select new com.toomeet.toomeet_play_api.dto.response.channel.UserChannelBasicInfoResponse(" +
            "c.id, " +
            "c.name, " +
            "c.avatar, " +
            "count(distinct sub.id), " +
            "case when count (distinct user_sub.id) > 0 then true else false end, " +
            "case when count (distinct user_member.id) > 0 then true else false end " +
            ") from Channel c " +
            "left join c.subscribers sub " +
            "left join c.subscribers user_sub on user_sub.id = :userId " +
            "left join  c.members user_member on user_member.id = :userId " +
            "where c.id = :channelId " +
            "group by c")
    UserChannelBasicInfoResponse getBasicInfo(String channelId, String userId);
}

