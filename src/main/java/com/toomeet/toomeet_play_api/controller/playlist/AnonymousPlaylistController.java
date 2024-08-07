/*
 *  AnonymousPlaylistController
 *  @author: Minhhieuano
 *  @created 8/7/2024 1:06 PM
 * */

package com.toomeet.toomeet_play_api.controller.playlist;

import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.service.playlist.AnonymousPlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/anonymous/playlists")
@RequiredArgsConstructor
public class AnonymousPlaylistController {
    private final AnonymousPlaylistService playlistService;

    @GetMapping("{playlistId}")
    public ResponseEntity<ApiResponse<?>> getPlaylistDetail(@PathVariable("playlistId") String playlistId) {
        ApiResponse<?> response = ApiResponse.success(playlistService.getPlaylistById(playlistId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getAllPlaylistByChannel(
            @RequestParam("channel") String channelId,
            @RequestParam(value = "p", required = false, defaultValue = "0") int page,
            @RequestParam(value = "l", required = false, defaultValue = "20") int limit) {
        ApiResponse<?> response =
                ApiResponse.success(playlistService.getAllPlayListByChannelId(channelId, page, limit));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
