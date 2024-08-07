package com.toomeet.toomeet_play_api.service.video;

import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.video.UserVideoDetailResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoNewsfeedResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoReactionResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.enums.ReactionType;

public interface UserVideoService {
    PageableResponse<VideoNewsfeedResponse> getNewsfeeds(int page, int limit, Account account);

    UserVideoDetailResponse getVideoDetails(String videoId, Account account);

    VideoReactionResponse reactionVideo(String videoId, ReactionType type, Account account);

    VideoReactionResponse unReactionVideo(String videoId, ReactionType type, Account account);
}
