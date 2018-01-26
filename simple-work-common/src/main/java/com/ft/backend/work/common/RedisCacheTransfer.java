package com.ft.backend.work.common;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * Created by huanghongyi on 2018/1/25.
 */
public class RedisCacheTransfer {
    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        RedisCache.setJedisConnectionFactory(jedisConnectionFactory);
    }
}
