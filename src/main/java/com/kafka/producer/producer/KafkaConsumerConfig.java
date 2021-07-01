package com.kafka.producer.producer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import java.util.HashMap;
import java.util.Map;



/**
 * @Auther: ShouZhi@Duan
 * @Description: 消费端参数配置
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    /**
     * 服务地址
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;
    /**
     * 分组id
     */
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    /**
     * 自动提交
     */
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private boolean enableAutoCommit;
    /**
     * 自动提交的间隔时间，默认值是 5000，单位是毫秒
     */
    @Value("${spring.kafka.consumer.auto-commit-interval}")
    private String autoCommitInterval;
    /**
     * 批量消费一次最大拉取的数据量
     */
    @Value("${spring.kafka.consumer.max-poll-records:10}")
    private int maxPollRecordsConfig;

    /**
     * 消费者配置
     * @return 配置map
     */
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>(10);
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        //propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecordsConfig);
        return propsMap;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);//多线程消费。例如当前topic有3个分区，则这里配置的3表示每个分区将有1个线程负责消费。
        factory.getContainerProperties().setPollTimeout(1500); //消息拉取阻塞时间1.5秒。表示如果在1.5秒内没有消息则连接超时断开。
        factory.setBatchListener(true); //启用批量消费和spring.kafka.consumer.max-poll-records配合使用。
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE); //开启真正手动提交模式
        return factory;
    }

    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

}
