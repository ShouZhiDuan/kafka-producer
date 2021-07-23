package com.kafka.producer.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 */
@Slf4j
public class DszConsumer3 {

    String topic = "test-topic-89";

    public DszConsumer3() {

    }

    @Test
    public void test() {
        KafkaConsumer<Integer, String> consumer;
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.10.33:9092,192.168.10.34:9092");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "dsz-DszConsumer3");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-DszConsumer3-1");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        //properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000"); //自动提交(批量确认)
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //一个新的group的消费者去消费一个topic。//earliest消费最近没有被消费的消息。latest消费历史所有的消息。
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumer = new KafkaConsumer<Integer, String>(properties);
        //订阅topic
        consumer.subscribe(Collections.singleton(this.topic));
        //每隔1秒拉取一批消息
        ConsumerRecords<Integer, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
        //分区列表
        Set<TopicPartition> assignment = consumer.assignment();
        consumerRecords.forEach(record -> {
            //log.info("消费消息======：" + record.key() + "->" + record.value() + "->" + record.offset());
            String topic = record.topic();
            int partition = record.partition();
            long offset = record.offset();
//            assignment.forEach(e -> {
//                consumer.seek(new TopicPartition(this.topic,e.partition()),2l);
//            });
            if (1 == partition && 2 == offset) {
                /**
                 * 指定分区offset消费
                 * 参考：https://blog.csdn.net/weixin_37150792/article/details/89851731
                 */
                log.info("提交topic={},partition={},offset={}消息。", topic, partition, offset);
                consumer.seek(new TopicPartition(this.topic, partition), 2l);
                consumer.commitSync();
            }
        });
        //consumer.commitSync();//同步提交(会重试)
    }
}
