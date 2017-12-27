package org.spring.springboot.schedule;

import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.spring.springboot.cache.HCacheMapUtil;
import org.spring.springboot.cache.HCacheQueueUtil;
import org.spring.springboot.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
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

    /**
     *队列放入数据
     */
    @Scheduled(cron = "0 53 9 * * *")
    public void hazecastQueue() {
        int i = HCacheQueueUtil.pushQueueData(hazelcast, "QUEUE", "HAZECASTQUEUE");
        int j = HCacheQueueUtil.pushQueueData(hazelcast, "QUEUE", "hazecast");
        log.info("第一次放入队列  " + i + "  第二次放入队列 " + j);
    }

    /**
     * 获取队列数据
     */
    @Scheduled(cron = "0 54 9 * * *")
    public void getQueue() {
        Integer total = 1;
        List<String> queue = HCacheQueueUtil.getQueueDataList(hazelcast, "QUEUE", total, String.class);
        log.info("获取队列中的信息  " + JsonUtil.objectToJson(queue));

        Integer amount = 2;
        List<String> list = HCacheQueueUtil.getQueueDataList(hazelcast, "QUEUE", total, String.class);
        log.info("获取队列中的信息  " + JsonUtil.objectToJson(list));
    }
}
