package com.toomeet.toomeet_play_api.service.channel.impl;

import com.toomeet.toomeet_play_api.dto.request.channel.AddVideoPlaylistRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.DeleteVideoPlaylistRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.NewPlaylistRequest;
import com.toomeet.toomeet_play_api.dto.request.channel.UpdatePlaylistRequest;
import com.toomeet.toomeet_play_api.dto.response.channel.PlaylistResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import com.toomeet.toomeet_play_api.entity.Playlist;
import com.toomeet.toomeet_play_api.entity.video.Video;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.PlaylistMapper;
import com.toomeet.toomeet_play_api.repository.ChannelRepository;
import com.toomeet.toomeet_play_api.repository.PlaylistRepository;
import com.toomeet.toomeet_play_api.repository.UserRepository;
import com.toomeet.toomeet_play_api.repository.video.VideoRepository;
import com.toomeet.toomeet_play_api.service.channel.PlaylistService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final PlaylistMapper playlistMapper;
    private final VideoRepository videoRepository;


    @Override
    public PlaylistResponse createPlaylist(NewPlaylistRequest request, Account account) {

        if (playlistRepository.existsByName(request.getName())) {
            throw new ApiException(ErrorCode.PLAYLIST_ALREADY_EXIST);
        }


        Playlist playlist = Playlist.builder()
                .description(request.getDescription())
                .name(request.getName())
                .channel(account.getChannel())
                .videos(new HashSet<>())
                .build();
        Playlist newPlaylist = playlistRepository.save(playlist);


        return playlistMapper.toPlaylistResponse(newPlaylist);
    }

    @Override
    public PlaylistResponse getPlayListById(String playlistId, Account account) {
        Playlist playlist = getPlaylistAnhCheckOwnership(playlistId, account);
        return playlistMapper.toPlaylistResponse(playlist);
    }

    @Override
    @Transactional
    public PlaylistResponse updatePlaylist(UpdatePlaylistRequest request, String playlistId, Account account) {

        Playlist playlist = getPlaylistAnhCheckOwnership(playlistId, account);

        playlist.setName(request.getName());
        playlist.setDescription(request.getDescription());


        Playlist updatedPlaylist = playlistRepository.save(playlist);

        return playlistMapper.toPlaylistResponse(updatedPlaylist);
    }


    @Override
    public String deletePlaylist(String playlistId, Account account) {
        Playlist playlist = getPlaylistAnhCheckOwnership(playlistId, account);

        playlistRepository.delete(playlist);

        return "Playlist has been deleted";
    }

    @Override
    @Transactional
    public String addVideoToPlaylist(AddVideoPlaylistRequest request, String playlistId, Account account) {

        Playlist playlist = getPlaylistAnhCheckOwnership(playlistId, account);

        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        if (playlist.getVideos().contains(video)) {
            throw new ApiException(ErrorCode.VIDEO_ALREADY_EXISTED_IN_PLAYLIST);
        }

        playlist.getVideos().add(video);

        playlistRepository.save(playlist);

        return "Video has been added to playlist";
    }

    @Override
    @Transactional
    public String deleteVideo(DeleteVideoPlaylistRequest request, String playlistId, Account account) {

        Playlist playlist = getPlaylistAnhCheckOwnership(playlistId, account);

        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new ApiException(ErrorCode.VIDEO_NOT_FOUND));

        if (!playlist.getVideos().contains(video)) {
            throw new ApiException(ErrorCode.VIDEO_NOT_FOUND_IN_PLAYLIST);
        }

        playlist.getVideos().remove(video);

        playlistRepository.save(playlist);

        return "Video has been deleted form the playlist";
    }


    private Playlist getPlaylistAnhCheckOwnership(String playlistId, Account account) {
        if (!playlistRepository.isPlaylistOwner(playlistId, account.getChannelId())) {
            throw new ApiException(ErrorCode.ACCESS_DENIED);
        }

        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));
    }


}
