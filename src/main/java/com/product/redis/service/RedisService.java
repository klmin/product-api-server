package com.product.redis.service;


import com.product.redis.exception.RedisRuntimeException;
import com.product.redis.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {
    
    private final RedisRepository redisRepository;

    public <T> T getOrInsertWithTTLSeconds(String key, Class<T> clazz, Callable<T> callback, long timeout){
        return getOrInsertWithTTL(key, clazz, callback, timeout, TimeUnit.SECONDS);
    }

    public <T> T getOrInsertWithTTLMinutes(String key, Class<T> clazz, Callable<T> callback, long timeout){
        return getOrInsertWithTTL(key, clazz, callback, timeout, TimeUnit.MINUTES);
    }

    public <T> T getOrInsertWithTTLHours(String key, Class<T> clazz, Callable<T> callback, long timeout){
        return getOrInsertWithTTL(key, clazz, callback, timeout, TimeUnit.HOURS);
    }

    private <T> T getOrInsertWithTTL(String key, Class<T> clazz, Callable<T> callback, long timeout, TimeUnit timeUnit) {

        try {
            return redisRepository.find(key, clazz).orElseGet(() -> callbackAndRedisInsert(key, callback, timeout, timeUnit));
        }catch(RedisRuntimeException e) {
            throw e;
        }catch(RedisConnectionFailureException e){
            log.error("Redis 서버에서 데이터를 가져오는 중 오류가 발생했습니다. callback을 실행합니다. [key] : {}", key, e);
            return callbackAndThrow(key, callback);
        } catch (Exception e) {
            log.error("오류가 발생했습니다. callback을 실행합니다. [key] : {}", key, e);
            return callbackAndRedisInsert(key, callback, timeout, timeUnit);

        }
        
    }

    private <T> T callbackAndRedisInsert(String key, Callable<T> callback, long timeout, TimeUnit timeUnit) {

        T result = callbackAndThrow(key, callback);

        try {
            redisRepository.insert(key, result, timeout, timeUnit);
        } catch (Exception e) {
            log.error("Redis에 데이터를 저장하는 중 오류가 발생했습니다. [key] : {}", key, e);
        }

        return result;
    }

    private <T> T callbackAndThrow(String key, Callable<T> callback){

        T result;

        try {
            result = callback.call();
        }catch(Exception e){
            log.error("callback 처리중 오류가 발생했습니다. [key] : {}", key, e);
            throw new RedisRuntimeException("callback 처리중 오류가 발생했습니다.");
        }

        if(result == null){
            log.error("callback 처리의 결과가 없습니다. [key] : {}", key);
            throw new RedisRuntimeException("callback 처리의 결과가 없습니다.");
        }

        return result;
    }


    public String get(String key){
        return redisRepository.get(key);
    }

    public <T> Optional<T> find(String key, Class<T> clazz){
        return redisRepository.find(key, clazz);
    }

    public void insert(String key, String value, long timeout, TimeUnit unit){
        redisRepository.insert(key, value, timeout, unit);
    }

    public void insert(String key, Object value, long timeout, TimeUnit unit){
        redisRepository.insert(key, value, timeout, unit);
    }


    public void delete(String key){
        redisRepository.delete(key);
    }

    public Long getKeyExpiration(String key){
        return redisRepository.getKeyExpiration(key);
    }

    public Boolean hasKey(String key){
        return redisRepository.hasKey(key);
    }



}
