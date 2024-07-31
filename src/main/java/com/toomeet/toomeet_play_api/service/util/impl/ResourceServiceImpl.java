package com.toomeet.toomeet_play_api.service.util.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.toomeet.toomeet_play_api.dto.uploader.ResourceUploaderResponse;
import com.toomeet.toomeet_play_api.service.util.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final Cloudinary cloudinary;

    @Value("${cloudinary.dir_prefix}")
    private String dirPrefix;

    @Value("${cloudinary.secure_url_expire_in}")
    private Long secureUrlExpireIn;

    @Override
    public ResourceUploaderResponse uploadImage(byte[] file, String publicId, String path) throws IOException {

        Map<?, ?> configs = ObjectUtils.asMap(
                "resource_type", "image",
                "folder", getCloudPath(path),
                "public_id", publicId,
                "overwrite", true,
                "type", "authenticated",
                "allowed_formats", "jpg,png,gif"
        );

        return upload(file, configs);
    }

    @Override
    public ResourceUploaderResponse uploadVideo(byte[] video, String publicId, String path) throws IOException {

        Map<?, ?> configs = ObjectUtils.asMap(
                "resource_type", "video",
                "folder", getCloudPath(path),
                "public_id", publicId,
                "overwrite", true,
                "type", "authenticated",
                "allowed_formats", "mp4"
        );

        return upload(video, configs);
    }

    @Override
    public String generateSignedUrl(String publicId) {

        Instant now = Instant.now();

        Map options = ObjectUtils.asMap(
                "resource_type", "image",
                "type", "authenticated",
                "expires_at", now.plus(secureUrlExpireIn, ChronoUnit.HOURS).toEpochMilli()
        );

        return cloudinary.apiSignRequest(options, "EB0sjDs0N22e-7gECIM3YpE_Kuo");
    }


    @Override
    public void deleteVideo(String publicId, String path) throws IOException {
        cloudinary.uploader().destroy(getCloudPath(path) + "/" + publicId, ObjectUtils.asMap("resource_type", "video"));
    }

    @Override
    public void deleteImage(String publicId, String path) throws IOException {


        cloudinary.uploader().destroy(getCloudPath(path) + "/" + publicId, ObjectUtils.asMap("resource_type", "image"));
    }

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


}
