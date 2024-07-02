package com.toomeet.toomeet_play_api.service.util.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.toomeet.toomeet_play_api.dto.uploader.ResourceUploaderResponse;
import com.toomeet.toomeet_play_api.service.util.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final Cloudinary cloudinary;

    @Value("${cloudinary.dir_prefix}")
    private String dirPrefix;

    private ResourceUploaderResponse upload(byte[] file, Map<?, ?> configs) throws IOException {
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file, configs);
        return ResourceUploaderResponse.builder()
                .publicId(uploadResult.get("public_id").toString())
                .etag(uploadResult.get("etag").toString())
                .format(uploadResult.get("format").toString())
                .width(Long.parseLong(uploadResult.get("width").toString()))
                .height(Long.parseLong(uploadResult.get("height").toString()))
                .resourceType(uploadResult.get("resource_type").toString())
                .secureUrl(uploadResult.get("secure_url").toString())
                .url(uploadResult.get("url").toString())
                .version(Long.parseLong(uploadResult.get("version").toString()))
                .signature(uploadResult.get("signature").toString())
                .build();
    }

    private String getCloudPath(String path) {
        return dirPrefix + (path.startsWith("/") ? path : "/" + path);
    }

    public ResourceUploaderResponse uploadImage(byte[] file, String publicId, String path) throws IOException {

        Map<?, ?> configs = ObjectUtils.asMap(
                "resource_type", "image",
                "folder", getCloudPath(path),
                "public_id", publicId,
                "allowed_formats", "jpg,png,gif"
        );

        return upload(file, configs);
    }

    public ResourceUploaderResponse uploadVideo(byte[] video, String publicId, String path) throws IOException {

        Map<?, ?> configs = ObjectUtils.asMap(
                "resource_type", "video",
                "folder", getCloudPath(path),
                "public_id", publicId,
                "allowed_formats", "mp4"
        );

        return upload(video, configs);
    }
}
