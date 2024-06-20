package com.toomeet.toomeet_play_api.security;

import com.toomeet.toomeet_play_api.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserDetailsService userDetailsService;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        String userId = jwt.getSubject();
        System.out.println(userId);
        User user = (User) userDetailsService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
