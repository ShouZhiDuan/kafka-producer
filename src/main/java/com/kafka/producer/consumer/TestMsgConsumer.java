package com.kafka.producer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 * 更多@KafkaListener操作清参考： https://blog.csdn.net/cowbin2012/article/details/85407495
 */
@Slf4j
@Component
public class TestMsgConsumer {

    private final static String TOPIC = "test-topic-88";

    /**
     * 不指定分区消费(单个串行消费)
     */
//    @KafkaListener(topics = {TOPIC},groupId = "TestMsgConsumer-666666-9")
//    public void test(ConsumerRecord record){
//        Optional<?> msg = Optional.ofNullable(record.value());
//        if(msg.isPresent()){
//            System.err.println(msg.get());
//        }
//    }


}



