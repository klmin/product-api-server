package com.product.redis.repository;

import com.product.objectmapper.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapperUtil objectMapperUtil;

    public void insert(String key, String value, long timeout, TimeUnit unit){
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public void insert(String key, Object value, long timeout, TimeUnit unit){
        redisTemplate.opsForValue().set(key, objectMapperUtil.writeValueAsString(value), timeout, unit);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T get(String key, Class<T> clazz) {
        return objectMapperUtil.readValue(redisTemplate.opsForValue().get(key), clazz);
    }

    public <T> Optional<T> find(String key, Class<T> clazz) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key))
                .map(value -> objectMapperUtil.readValue(value, clazz));
    }

    public Long getKeyExpiration(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MINUTES);
    }

    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
