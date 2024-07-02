package com.toomeet.toomeet_play_api.service;

import com.toomeet.toomeet_play_api.dto.uploader.ResourceUploaderResponse;

import java.io.IOException;

public interface ResourceService {
    ResourceUploaderResponse uploadVideo(byte[] video, String publicId, String path) throws IOException;

    ResourceUploaderResponse uploadImage(byte[] image, String publicId, String path) throws IOException;
}
