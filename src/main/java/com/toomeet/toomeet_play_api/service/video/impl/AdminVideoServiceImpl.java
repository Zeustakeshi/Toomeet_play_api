package com.toomeet.toomeet_play_api.service.video.impl;

import com.toomeet.toomeet_play_api.dto.request.video.CreateVideoCategoryRequest;
import com.toomeet.toomeet_play_api.dto.response.video.VideoCategoryResponse;
import com.toomeet.toomeet_play_api.entity.video.Category;
import com.toomeet.toomeet_play_api.enums.ErrorCode;
import com.toomeet.toomeet_play_api.exception.ApiException;
import com.toomeet.toomeet_play_api.mapper.VideoCategoryMapper;
import com.toomeet.toomeet_play_api.repository.video.category.CategoryRepository;
import com.toomeet.toomeet_play_api.service.video.AdminVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminVideoServiceImpl implements AdminVideoService {

    private final CategoryRepository categoryRepository;
    private final VideoCategoryMapper categoryMapper;

    @Override
    public VideoCategoryResponse createCategory(CreateVideoCategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new ApiException(ErrorCode.CATEGORY_NAME_ALREADY_EXISTS);
        }
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        Category newCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(newCategory);
    }
}
