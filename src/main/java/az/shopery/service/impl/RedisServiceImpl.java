package az.shopery.service.impl;

import az.shopery.service.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void set(String key, Object value, Duration ttl) {
        try {
            String json = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, json, ttl);
        } catch (Exception e) {
            //TODO: better exception handling
            throw new RuntimeException("Failed to write to Redis!", e);
        }
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> clazz) {
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (Objects.isNull(json)) {
                return Optional.empty();
            }
            return Optional.of(objectMapper.readValue(json, clazz));
        } catch (Exception e) {
            //TODO: better exception handling
            throw new RuntimeException("Failed to read from Redis!", e);
        }
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
