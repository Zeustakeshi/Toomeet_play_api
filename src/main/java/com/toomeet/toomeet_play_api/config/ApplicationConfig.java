package com.toomeet.toomeet_play_api.config;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Converters.registerLocalDateTime(gsonBuilder);
        return gsonBuilder.create();
    }
}
