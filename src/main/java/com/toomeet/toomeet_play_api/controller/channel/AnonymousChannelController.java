/*
 *  AnonymousChannelController
 *  @author: Minhhieuano
 *  @created 7/25/2024 1:55 PM
 * */

package com.toomeet.toomeet_play_api.controller.channel;

import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.service.channel.AnonymousChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/anonymous/channels")
@RequiredArgsConstructor
public class AnonymousChannelController {

    private final AnonymousChannelService channelService;

    @GetMapping("{channelId}/basic")
    public ResponseEntity<ApiResponse<?>> getChannel(@PathVariable("channelId") String channelId) {
        ApiResponse<?> response = ApiResponse.success(channelService.getBasicInfo(channelId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
