package com.toomeet.toomeet_play_api.event.listener;

import com.toomeet.toomeet_play_api.event.DeleteVideoResourceEvent;
import com.toomeet.toomeet_play_api.service.video.StudioVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceDeleteListener {

    private final StudioVideoService studioVideoService;

    @EventListener
    public void handleDeleteVideoResource(DeleteVideoResourceEvent event) {
        studioVideoService.deleteVideoResourceAsync(event.getPublicId());
    }
}
