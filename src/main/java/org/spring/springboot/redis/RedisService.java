package org.spring.springboot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author JE哥
 * @email 1272434821@qq.com
 * @description:常规操作
 */
@Service
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    // 批量删除对应的value
    public void deleteAll(String... keys) {
        for (String key : keys) {
            delete(key);
        }
    }

    // 批量删除key
    public void deletePattern(String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    // 删除指定key的value
    public void delete(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    // 判断缓存中是否有对应的value
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    // 读取缓存
    public Object get(String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    // 写入缓存
    public boolean set(String key, Object value) {
        boolean flag = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // 写入缓存
    public boolean set(String key, Object value, Long expireTime) {
        boolean flag = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // key 增量整数值,不传默认增加1 ,小于
    public Long increment(String key, Long delta) {
        boolean flag = false;
        if (null == delta) {
            delta = 1L;
        }
        Class<StringRedisSerializer> stringRedisSerializerClass = StringRedisSerializer.class;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            Long increment = operations.increment(key, delta);
            flag = true;
            return increment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // key 增量小数值
    public Double increment(String key, double delta) {
        boolean flag = false;
        Class<StringRedisSerializer> stringRedisSerializerClass = StringRedisSerializer.class;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            Double increment = operations.increment(key, delta);
            flag = true;
            return increment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
