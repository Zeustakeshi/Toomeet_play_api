package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.UserAuthenticationResponse;
import com.toomeet.toomeet_play_api.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserAuthenticationResponse toUserAuthenticationResponse(User user);
}
