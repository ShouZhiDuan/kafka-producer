package com.kafka.producer.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: ShouZhi@Duan
 * @Description: 生产者参数配置
 */
@EnableKafka
@Configuration
public class KafkaProducerConfig {
    /**
     * 服务地址
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;
    /**
     * 重试失败的发送次数
     */
    @Value("${spring.kafka.producer.retries}")
    private int retries;
    /**
     * 批量数据大小
     */
    @Value("${spring.kafka.producer.batch-size}")
    private int batchSize;
    /**
     * 缓冲内存大小
     */
    @Value("${spring.kafka.producer.buffer-memory}")
    private int bufferMemory;

    /**
     * 生产者配置
     *
     * @return 配置map
     */
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        //props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, "false"); // 设置不包含 header,节省磁盘空间
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        KafkaTemplate kafkaTemplate = new KafkaTemplate(producerFactory());
        //设置全局消息发送监听器
        kafkaTemplate.setProducerListener(producerListener());
        kafkaTemplate.setMessageConverter(customRecordMessageConverter());
        return kafkaTemplate;
    }

    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * 消息监听器
     */
    @Bean
    public MsgProducerListener producerListener() {
        return new MsgProducerListener();
    }

    /**
     * 消息拦截器(转换器)
     */
    @Bean
    public CustomRecordMessageConverter customRecordMessageConverter() {
        return new CustomRecordMessageConverter();
    }


}
