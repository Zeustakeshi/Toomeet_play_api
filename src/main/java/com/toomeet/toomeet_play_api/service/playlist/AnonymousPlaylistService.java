/*
 *  AnonymousPlaylistService
 *  @author: Minhhieuano
 *  @created 8/7/2024 1:06 PM
 * */

package com.toomeet.toomeet_play_api.service.playlist;

import com.toomeet.toomeet_play_api.dto.response.general.PageableResponse;
import com.toomeet.toomeet_play_api.dto.response.playlist.PlaylistResponse;

public interface AnonymousPlaylistService {

    PlaylistResponse getPlaylistById(String playlistId);

    PageableResponse<PlaylistResponse> getAllPlayListByChannelId(String channelId, int page, int limit);
}
