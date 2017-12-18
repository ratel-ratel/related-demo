package org.spring.springboot.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * CacheMap 数值操作工具类
 * <p>
 * Created by admin on 2016/12/28.
 */
@Slf4j
public class HCacheMapUtil {

    /**
     * 写入hazelcast map缓存
     *
     * @param hInstance HazelcastInstance实例对象
     * @param namespace cache的命名空间
     * @param cacheKey  cache数据的键
     * @param data      cache数据对象
     * @param <T>
     * @return
     */
    public static <T> int put(HazelcastInstance hInstance, String namespace, String cacheKey, T data) {
        if (null == hInstance || StringUtils.isEmpty(namespace)
                || StringUtils.isEmpty(cacheKey) || null == data) {
            return OperateFlagConstant.INTEGER_FALSE;
        }
        // 获取cache IMap对象
        IMap<String, T> cacheMap = hInstance.getMap(namespace);
        cacheMap.put(cacheKey, data);
        return OperateFlagConstant.INTEGER_TRUE;
    }

    /**
     *
     * @param hInstance
     * @param namespace
     * @param cacheKey
     * @param data
     * @param expired  时间秒
     * @param <T>
     * @return
     */
    public static <T> int put(HazelcastInstance hInstance, String namespace, String cacheKey, T data, long expired) {
        if (null == hInstance || StringUtils.isEmpty(namespace)
                || StringUtils.isEmpty(cacheKey) || null == data) {
            return OperateFlagConstant.INTEGER_FALSE;
        }
        // 获取cache IMap对象
        IMap<String, T> cacheMap = hInstance.getMap(namespace);
        cacheMap.put(cacheKey, data, expired, TimeUnit.SECONDS);
        return OperateFlagConstant.INTEGER_TRUE;
    }

    /**
     * 获取hazelcast map缓存
     *
     * @param hInstance HazelcastInstance实例对象
     * @param namespace cache的命名空间
     * @param cacheKey  cache数据的键
     * @param cls       对象类
     * @param <T>
     * @return
     */
    public static <T> T get(HazelcastInstance hInstance, String namespace, String cacheKey, Class<T> cls) {
        if (null == hInstance || StringUtils.isEmpty(namespace)
                || StringUtils.isEmpty(cacheKey) || null == cls) {
            return null;
        }

        // 获取cache IMap对象
        IMap<String, T> cacheMap = hInstance.getMap(namespace);
        return cacheMap.get(cacheKey);
    }

    /**
     * 清除hazelcast map缓存
     *
     * @param hInstance HazelcastInstance实例对象
     * @param namespace cache的命名空间
     * @param cacheKey  cache数据的键
     * @return
     */
    public static int remove(HazelcastInstance hInstance, String namespace, String cacheKey) {
        if (null == hInstance || StringUtils.isEmpty(namespace)
                || StringUtils.isEmpty(cacheKey)) {
            return OperateFlagConstant.INTEGER_FALSE;
        }

        // 获取cache IMap对象
        IMap<String, Object> cacheMap = hInstance.getMap(namespace);
        cacheMap.remove(cacheKey);
        return OperateFlagConstant.INTEGER_TRUE;
    }
}
