package com.product.redis.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisRepositoryTest {

    @Autowired
    RedisRepository redisRepository;
    
    @DisplayName("등록 확인 후 삭제 확인 테스트")
    @Test
    void test() {
        String key = "testkey1";
        String value = "testvalue1";
        redisRepository.insert(key, value, 1L, TimeUnit.MINUTES);

        assertEquals(value, redisRepository.get(key));
        redisRepository.delete(key);
        assertNull(redisRepository.get(key));
    }



}