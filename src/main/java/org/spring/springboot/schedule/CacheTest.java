package org.spring.springboot.schedule;

import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.spring.springboot.cache.HCacheMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * Created by nice on 2017/12/14.
 */
@Slf4j
@Service
@EnableScheduling //开启定时任务支持
public class CacheTest {
    @Autowired
    private HazelcastInstance hazelcast;
    @Scheduled(cron = "0 57 17 * * *")
    public void hazecastTest() {
        String key = "hello Word";
        log.info("放入缓存开始.....");
        int put = HCacheMapUtil.put(hazelcast, "HAZELCAST", "key", key);
        log.info("放入缓存结果是 : " + put);
        String str = HCacheMapUtil.get(hazelcast, "HAZELCAST", "key", String.class);
        log.info("从缓存中获取的结果为 : " + str);
    }
}
