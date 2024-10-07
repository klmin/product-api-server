package com.product.redis.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisCacheNames {
    private static final String CACHE_PREFIX  = "cache:";
    public static final String USER_STATUS = CACHE_PREFIX  + "user:status:";
    public static final String JWT_REFRESH_TOKEN = CACHE_PREFIX  + "jwt:refresh-token:";
    public static final String JWT_BLACK_LIST_TOKEN = CACHE_PREFIX  + "jwt:logout-token:";

    public static String generateCacheKey(String prefix, Object id){
        return prefix + id;
    }

    public static String generateUserStatusCacheKey(Object id){
        return generateCacheKey(USER_STATUS, id);
    }

    public static String generateJwtRefreshTokenCacheKey(Object id){
        return generateCacheKey(JWT_REFRESH_TOKEN, id);
    }
    public static String generateJwtBlackListTokenCacheKey(Object token){
        return generateCacheKey(JWT_BLACK_LIST_TOKEN, token);
    }


}
