package com.kafka.producer.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 */
@Slf4j
public class DszConsumer2 {

    String topic = "test-topic-3";

    public DszConsumer2() {

    }

    @Test
    public void test() {
        KafkaConsumer<Integer, String> consumer;
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.10.33:9092,192.168.10.34:9092");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "dsz-consumer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "dsz-consumer-group-1");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000"); //自动提交(批量确认)
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //一个新的group的消费者去消费一个topic。//earliest消费最近没有被消费的消息。latest消费历史所有的消息。
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumer = new KafkaConsumer<Integer, String>(properties);
        //订阅topic
        consumer.subscribe(Collections.singleton(this.topic));
        //每隔1秒拉取一批消息
        ConsumerRecords<Integer, String> consumerRecords = consumer.poll(0);
        //分区列表
        Set<TopicPartition> assignment = consumer.assignment();
        consumerRecords.forEach(record -> {
            log.info("消费消息======：" + record.key() + "->" + record.value() + "->" + record.offset());
        });
        consumer.commitSync();//同步提交(会重试)
    }
}
