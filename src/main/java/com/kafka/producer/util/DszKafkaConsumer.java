package com.kafka.producer.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 */
@Slf4j
public class DszKafkaConsumer extends Thread{

    KafkaConsumer<Integer,String> consumer;
    String topic;

    public DszKafkaConsumer(String topic) {
        Properties properties=new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.10.33:9092,192.168.10.34:9092");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG,"dsz-consumer-89-5");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"dsz-consumer-group-89-5");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"30000");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000"); //自动提交(批量确认)
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //一个新的group的消费者去消费一个topic。//earliest 消费最近没有被消费的消息。latest 消费历史所有的消息。
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");
        consumer=new KafkaConsumer<Integer, String>(properties);
        this.topic = topic;
    }

    @Override
    public void run() {
        //订阅topic
        consumer.subscribe(Collections.singleton(this.topic));
        while(true){
            //每隔1秒拉取一批消息
            ConsumerRecords<Integer,String> consumerRecords = consumer.poll(Duration.ofMillis(1));
            //分区列表
            Set<TopicPartition> assignment = consumer.assignment();
            if(!consumerRecords.isEmpty()){
                System.out.println("============================消费到消息==============================");
            }
            consumerRecords.forEach(record->{
                System.err.println("================消费消息================：" + record.key() + "->" + record.value() + "->" + record.offset());
            });
        }
        //consumer.commitAsync(); //异步手动提交
    }

    public static void main(String[] args) {
        new DszKafkaConsumer("test-topic-89").start();
    }
}
