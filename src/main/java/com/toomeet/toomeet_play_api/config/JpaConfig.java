package com.toomeet.toomeet_play_api.config;

import com.toomeet.toomeet_play_api.entity.auditing.AuditorAwareImpl;
import com.toomeet.toomeet_play_api.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {
    @Bean
    public AuditorAwareImpl auditorAware(UserService userService) {
        return new AuditorAwareImpl(userService);
    }
}
