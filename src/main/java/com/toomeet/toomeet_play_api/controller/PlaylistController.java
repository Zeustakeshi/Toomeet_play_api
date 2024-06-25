package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.request.CreatePlaylistRequest;
import com.toomeet.toomeet_play_api.dto.response.ApiResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.service.PlaylistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping(
    )
    public ResponseEntity<ApiResponse<?>> getAllPlayList(
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(playlistService.getAllPlayList(account.getUserId()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{playlistId}/size")
    public ResponseEntity<ApiResponse<?>> getPlaylistSize(
            @PathVariable String playlistId
    ) {
        ApiResponse<?> response = ApiResponse.success(playlistService.getPlaylistSize(playlistId));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createPlaylist(
            @RequestBody @Valid CreatePlaylistRequest createPlaylistRequest,
            @AuthenticationPrincipal Account account
    ) {
        ApiResponse<?> response = ApiResponse.success(playlistService.createPlaylist(createPlaylistRequest, account.getUserId()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
