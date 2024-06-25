package com.toomeet.toomeet_play_api.dto.playlist;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PlaylistBasicInfo {
    private String playlistId;
    private String name;
    private String ownerId;
}
