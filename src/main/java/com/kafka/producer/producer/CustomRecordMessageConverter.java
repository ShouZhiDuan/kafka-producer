package com.kafka.producer.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.messaging.Message;

import java.lang.reflect.Type;

/**
 * @Auther: ShouZhi@Duan
 * @Description: 消息转换器：可以理解为发送前，消费前的消息处理
 */
@Slf4j
public class CustomRecordMessageConverter implements RecordMessageConverter {

    @Override
    public Message<?> toMessage(ConsumerRecord<?, ?> consumerRecord, Acknowledgment acknowledgment, Consumer<?, ?> consumer, Type type) {
        log.info("======消息消费前======");
        return null;
    }

    @Override
    public ProducerRecord<?, ?> fromMessage(Message<?> message, String s) {
        log.info("======消息发送前======");
        return null;
    }
}
