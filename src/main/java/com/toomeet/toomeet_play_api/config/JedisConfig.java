package com.toomeet.toomeet_play_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class JedisConfig {

    @Value("${cache.redis_url}")
    private String redisUrl;

    @Bean
    public Jedis jedis() {
        return new Jedis(redisUrl);
    }
}
