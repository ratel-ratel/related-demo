//package org.spring.springboot.kafka;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
///**
// * 监听kafka队列消息
// *
// *Created by DELL on 2017/5/24.
// */
//@Slf4j
//@Service
//public class KafkaReceiver {
//    /**
//     * 监听kafka消息受理业务
//     */
//    @KafkaListener(topics ="${kafka.topics[0]}")
//    public void listenerKafkaProductCommit(ConsumerRecord<String, String> record) {
//        try {
//            log.info("kafka listen topic is: {} ",record.topic());
//            log.info("kafka listen partition is:{}",record.partition());
//            String value = record.value();
//            log.info("监听得到的结果" +value);
////            Thread.sleep(6000L);  睡眠 6 秒
//        } catch (Exception e) {
//            log.error("消息队列办理业务异常", e);
//        }
//    }
//}
