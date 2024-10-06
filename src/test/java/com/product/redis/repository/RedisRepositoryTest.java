package com.product.redis.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisRepositoryTest {

    @Autowired
    RedisRepository redisRepository;

    @Test
    void test() {
        String key = "testkey1";
        String value = "testvalue1";
        redisRepository.save(key, value);

        assertEquals(value, redisRepository.find(key));
        redisRepository.delete(key);
        assertNull(redisRepository.find(key));
    }



}