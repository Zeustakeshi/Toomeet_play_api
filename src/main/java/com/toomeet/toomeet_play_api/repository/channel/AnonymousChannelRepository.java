/*
 *  AnonymousChannelRepository
 *  @author: Minhhieuano
 *  @created 8/1/2024 12:08 AM
 * */

package com.toomeet.toomeet_play_api.repository.channel;

import com.toomeet.toomeet_play_api.dto.response.channel.AnonymousChannelBasicInfoResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnonymousChannelRepository extends ChannelRepository {
    @Query("select new com.toomeet.toomeet_play_api.dto.response.channel.AnonymousChannelBasicInfoResponse(" + "c.id, "
            + "c.name, "
            + "c.avatar, "
            + "count(distinct sub.id)"
            + ") from Channel c "
            + "left join c.subscribers sub "
            + "where c.id = :channelId "
            + "group by c")
    AnonymousChannelBasicInfoResponse getBasicInfo(String channelId);
}
