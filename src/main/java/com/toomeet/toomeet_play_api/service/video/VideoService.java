package com.toomeet.toomeet_play_api.service.video;

import com.toomeet.toomeet_play_api.dto.response.video.VideoInteractionResponse;
import com.toomeet.toomeet_play_api.dto.response.video.VideoReactionResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.enums.ReactionType;

public interface VideoService {
    VideoReactionResponse reactionVideo(String videoId, ReactionType type, Account account);

    VideoReactionResponse unReactionVideo(String videoId, ReactionType type, Account account);

    VideoInteractionResponse getVideoInteraction(String videoId, Account account);
}
