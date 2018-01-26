package com.ft.backend.work.service.impl.redis;

import org.apache.logging.log4j.util.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Created by huanghongyi on 2018/1/25.
 */
@Component("redisService")
public class RedisUtilsServiceImpl implements RedisUtilsService{

    @Autowired
    private StringRedisTemplate redisTemplate;


    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <T> void put(String key, T obj) {
        redisTemplate.opsForValue().set(key, JsonUtils.toJson(obj));
    }

    @Override
    public <T> void put(String key, T obj, int timeout) {
        put(key, obj, timeout, TimeUnit.MINUTES);
    }

    @Override
    public <T> void put(String key, T obj, int timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, JsonUtils.toJson(obj), timeout, unit);
    }

    @Override
    public <T> T get(String key, Class<T> cls) {
        return JsonUtils.fromJson(redisTemplate.opsForValue().get(key), cls);
    }

    @Override
    public <E, T extends Collection<E>> T get(String key, Class<E> cls, Class<T> collectionClass) {
        return JsonUtils.fromJson(redisTemplate.opsForValue().get(key), cls, collectionClass);
    }

    @Override
    public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier) {
        T t = get(key, cls);
        if (t == null) {
            t = supplier.get();
            if (t != null) {
                put(key, t);
            }
        }
        return t;
    }

    @Override
    public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier, int timeout) {
        T t = get(key, cls);
        if (t == null) {
            t = supplier.get();
            if (t != null) {
                put(key, t, timeout);
            }
        }
        return t;
    }

    @Override
    public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier, int timeout, TimeUnit unit) {
        T t = get(key, cls);
        if (t == null) {
            t = supplier.get();
            if (t != null) {
                put(key, t, timeout, unit);
            }
        }
        return t;
    }

    @Override
    public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier, int timeout, TimeUnit unit, boolean refresh) {
        T t = get(key, cls);
        if (t == null) {
            t = supplier.get();
            if (t != null) {
                put(key, t, timeout, unit);
            }
        } else {
            if (refresh) {
                expire(key, timeout, unit);
            }
        }
        return t;
    }

    @Override
    public <E, T extends Collection<E>> T putIfAbsent(String key, Class<E> cls, Class<T> collectionCls, Supplier<T> supplier) {
        T t = get(key, cls, collectionCls);
        if (t == null || t.isEmpty()) {
            t = supplier.get();
            if (t != null && t.size() > 0) {
                put(key, t);
            }
        }
        return t;
    }

    @Override
    public <E, T extends Collection<E>> T putIfAbsent(String key, Class<E> cls, Class<T> collectionCls, Supplier<T> supplier, int timeout) {
        return putIfAbsent(key, cls, collectionCls, supplier, timeout, TimeUnit.SECONDS);
    }

    @Override
    public <E, T extends Collection<E>> T putIfAbsent(String key, Class<E> cls, Class<T> collectionCls, Supplier<T> supplier, int timeout, TimeUnit unit) {
        return putIfAbsent(key, cls, collectionCls, supplier, timeout, unit, false);
    }

    @Override
    public <E, T extends Collection<E>> T putIfAbsent(String key, Class<E> cls, Class<T> collectionCls, Supplier<T> supplier, int timeout, TimeUnit unit, boolean refresh) {
        T t = get(key, cls, collectionCls);
        if (t == null || t.isEmpty()) {
            t = supplier.get();
            if (t != null && t.size() > 0) {
                put(key, t, timeout, unit);
            }
        } else {
            if (refresh) {
                expire(key, timeout, unit);
            }
        }
        return t;
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    @Override
    public boolean expire(String key, long timeout) {
        return expire(key, timeout, TimeUnit.MINUTES);
    }

    @Override
    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void put(String key, String value, int timeout) {
        put(key, value, timeout, TimeUnit.MINUTES);
    }

    @Override
    public void put(String key, String value, int timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }


    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
