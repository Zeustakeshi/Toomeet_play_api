package com.toomeet.toomeet_play_api.controller;

import com.toomeet.toomeet_play_api.dto.request.CreatePlaylistRequest;
import com.toomeet.toomeet_play_api.dto.response.ApiResponse;
import com.toomeet.toomeet_play_api.entity.User;
import com.toomeet.toomeet_play_api.service.PlayListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlayListService playListService;

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createPlayList(
            @RequestBody @Valid CreatePlaylistRequest createPlaylistRequest,
            @AuthenticationPrincipal User user
    ) {
        ApiResponse<?> response = ApiResponse.success(playListService.createPlayList(createPlaylistRequest, user));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
