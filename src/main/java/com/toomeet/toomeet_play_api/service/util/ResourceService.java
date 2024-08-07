package com.toomeet.toomeet_play_api.service.util;

import com.toomeet.toomeet_play_api.dto.uploader.ResourceUploaderResponse;
import java.io.IOException;

public interface ResourceService {
    ResourceUploaderResponse uploadVideo(byte[] video, String publicId, String path) throws IOException;

    ResourceUploaderResponse uploadImage(byte[] image, String publicId, String path) throws IOException;

    void deleteVideo(String publicId, String path) throws IOException;

    void deleteImage(String publicId, String path) throws IOException;

    String generateSignedUrl(String publicId);
}
