package com.toomeet.toomeet_play_api.event.listener;

import com.toomeet.toomeet_play_api.event.UploadVideoEvent;
import com.toomeet.toomeet_play_api.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoEventListener {

    private final VideoService videoService;

    @EventListener
    public void handleVideoUploadEvent(UploadVideoEvent event) {
        videoService.uploadVideo(event.getVideoId(), event.getUserId(), event.getVideo());
    }

}
