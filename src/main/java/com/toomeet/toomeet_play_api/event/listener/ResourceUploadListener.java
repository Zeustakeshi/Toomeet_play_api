package com.toomeet.toomeet_play_api.event.listener;


import com.toomeet.toomeet_play_api.event.UploadChannelAvatarEvent;
import com.toomeet.toomeet_play_api.event.UploadVideoEvent;
import com.toomeet.toomeet_play_api.event.UploadVideoThumbnailEvent;
import com.toomeet.toomeet_play_api.service.channel.ChannelService;
import com.toomeet.toomeet_play_api.service.video.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceUploadListener {
    private final VideoService videoService;
    private final ChannelService channelService;

    @EventListener
    public void handleVideoUploadEvent(UploadVideoEvent event) {
        videoService.uploadVideoAsync(event.getVideoId(), event.getUserId(), event.getVideo());
    }

    @EventListener
    public void handleChannelAvatarUploadEvent(UploadChannelAvatarEvent event) {
        channelService.updateChannelAvatarAsync(event.getAvatar(), event.getChannelId(), event.getUserId());
    }


    @EventListener
    public void handleVideoThumbnailUploadEvent(UploadVideoThumbnailEvent event) {
        videoService.uploadThumbnailAsync(event.getThumbnail(), event.getVideoId(), event.getUserId());
    }

}
