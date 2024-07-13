package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.request.channel.AddVideoPlaylistRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.DeleteVideoPlaylistRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.NewPlaylistRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.UpdatePlaylistRequest;
import com.toomeet.toomeet_play_api.dto.response.general.ApiResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.service.channel.PlaylistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createPlaylist(
            @RequestBody @Valid NewPlaylistRequest request,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(
                playlistService.createPlaylist(request, account)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("{playlistId}")
    public ResponseEntity<ApiResponse<?>> getPlaylistById(
            @AuthenticationPrincipal Account account,
            @PathVariable("playlistId") String playlistId
    ) {
        ApiResponse<?> response = ApiResponse.success(
                playlistService.getPlayListById(playlistId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/{playlistId}/video")
    public ResponseEntity<ApiResponse<?>> addVideoToPlaylist(
            @AuthenticationPrincipal Account account,
            @RequestBody() AddVideoPlaylistRequest request,
            @PathVariable("playlistId") String playlistId

    ) {
        ApiResponse<?> response = ApiResponse.success(
                playlistService.addVideoToPlaylist(request, playlistId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{playlistId}/video")
    public ResponseEntity<ApiResponse<?>> deleteVideoFormPlaylist(
            @AuthenticationPrincipal Account account,
            @RequestBody() DeleteVideoPlaylistRequest request,
            @PathVariable("playlistId") String playlistId
    ) {
        ApiResponse<?> response = ApiResponse.success(
                playlistService.deleteVideo(request, playlistId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("{playlistId}")
    public ResponseEntity<ApiResponse<?>> updatePlaylist(
            @AuthenticationPrincipal Account account,
            @RequestBody @Valid UpdatePlaylistRequest request,
            @PathVariable("playlistId") String playlistId
    ) {
        ApiResponse<?> response = ApiResponse.success(
                playlistService.updatePlaylist(request, playlistId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{playlistId}")
    public ResponseEntity<ApiResponse<?>> deletePlaylist(
            @AuthenticationPrincipal Account account,
            @PathVariable("playlistId") String playlistId
    ) {
        ApiResponse<?> response = ApiResponse.success(
                playlistService.deletePlaylist(playlistId, account)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
