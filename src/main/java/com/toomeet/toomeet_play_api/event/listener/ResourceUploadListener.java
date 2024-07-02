package com.toomeet.toomeet_play_api.event.listener;


import com.toomeet.toomeet_play_api.event.UploadVideoEvent;
import com.toomeet.toomeet_play_api.service.video.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceUploadListener {
    private final VideoService videoService;

    @EventListener
    public void handleVideoUploadEvent(UploadVideoEvent event) {
        videoService.uploadVideoAsync(event.getVideoId(), event.getUserId(), event.getVideo());
    }

}
