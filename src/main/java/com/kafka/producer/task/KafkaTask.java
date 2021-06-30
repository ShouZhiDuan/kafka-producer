package com.kafka.producer.task;

import com.kafka.producer.producer.MsgProducerListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListConsumerGroupOffsetsResult;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 */
@Slf4j
@Component
@EnableScheduling
public class KafkaTask {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private MsgProducerListener producerListener;

    private static int counter;

    @Value("${fillersmart.analyze.car.topic.consumer}")
    private String topic;

    @Scheduled(fixedRate = 3000)
    public void schedule(){
        long start = System.currentTimeMillis();
        counter = ++counter;
         kafkaTemplate.send(topic, "【msg-" + (counter) + "】");
        long end = System.currentTimeMillis();
        log.info("【生产者】执行发送消息，耗时：" + (end-start) + "ms");
    }

}
