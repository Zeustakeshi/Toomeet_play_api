package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.user.UserOverviewResponse;
import com.toomeet.toomeet_play_api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.account.image", target = "avatar")
    @Mapping(source = "user.account.name", target = "name")
    UserOverviewResponse toUserOverviewResponse(User user);

}
