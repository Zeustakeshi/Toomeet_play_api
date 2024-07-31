package com.toomeet.toomeet_play_api.mapper;

import com.toomeet.toomeet_play_api.dto.response.account.AccountResponse;
import com.toomeet.toomeet_play_api.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {


    @Mapping(source = "account.userId", target = "userId")
    AccountResponse toAccountResponse(Account account);
}
