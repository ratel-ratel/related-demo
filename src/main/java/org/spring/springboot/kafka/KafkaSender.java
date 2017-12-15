package org.spring.springboot.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

/**
 * 发布消息到kafka
 *
 * @author wangliang
 * @since 2017/5/18
 */
@Slf4j
@Service
@EnableScheduling //开启定时任务支持
public class KafkaSender {

    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Value("${spring.kafka.topics[0]}")
    private String rechargeTopic;

    /**
     * 发布kafka消息 进行确认提交
     */
    @Scheduled(cron = "0 25 15 * * *")
    public void sendKafka() {
        String send = UUID.randomUUID().toString();
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(rechargeTopic, "recharge", send);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("kafka消息放入队列成功");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("kafka消息放入队列失败");
            }
        });
    }

}
