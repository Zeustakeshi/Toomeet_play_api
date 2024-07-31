package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.user.UserBasicInfoResponse;
import com.toomeet.toomeet_play_api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.account.image", target = "avatar")
    @Mapping(source = "user.account.name", target = "name")
    UserBasicInfoResponse toUserOverviewResponse(User user);

}
