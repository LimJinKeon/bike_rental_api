/*
package bike_rental_api.config;

import bike_rental_api.oauth23.userInfo.BikeStation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, List<BikeStation>> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, List<BikeStation>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}*/
