package com.kafka.producer.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: ShouZhi@Duan
 * @Description: 消息发送回调
 */
@Slf4j
public class MsgProducerListener<K,V> implements ProducerListener<K,V> {

    public void onSuccess(String topic, Integer partition, K key, V value, RecordMetadata recordMetadata) {
        //发送成功
        log.info("【成功发送】" + "主题：" + topic + "消息：" + value);
    }

    public void onError(String topic, Integer partition, K key, V value, Exception exception) {
        //发送失败
        log.info("【失败发送】" + "主题：" + topic + "消息：" + value);
    }
}
