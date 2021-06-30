package com.kafka.producer.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: ShouZhi@Duan
 * @Description: 消息发送回调
 */
@Slf4j
public class MsgProducerListener<K,V> implements ProducerListener<K,V> {

    @Override
    public void onSuccess(ProducerRecord<K, V> producerRecord, RecordMetadata recordMetadata) {
        log.info("【成功发送】" + "主题：" + producerRecord.topic() + "消息：" + producerRecord.value());
    }

    @Override
    public void onError(ProducerRecord<K, V> producerRecord, RecordMetadata recordMetadata, Exception exception) {
        log.info("【失败发送】" + "主题：" + producerRecord.topic() + "消息：" + producerRecord.value());
    }
}
