package com.kafka.producer.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: ShouZhi@Duan
 * @Description: kafka同步异步发送
 */
@Slf4j
public class DszKafkaProducer extends Thread{

    private KafkaProducer producer;
    private String topic;

    public DszKafkaProducer(String topic) {
        this.topic = topic;
        Properties properties=new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.10.33:9092,192.168.10.34:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"dsz-producer");
        //properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.kafka.producer.partition.MyPartition");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        //连接的字符串  通过工厂  new
        producer=new KafkaProducer<Integer, String>(properties);
    }

    @Override
    public void run() {
        int num=0;
        while(num<6) {
            try {
                String msg="dsz kafka practice msg:"+num;
                //get 会拿到发送的结果
                //同步 get() -> Future()
                //回调通知
                Object o = producer.send(
                        //消息体
                        new ProducerRecord<>(topic, 2, "测试消息test-topic-88-msg-" + num),
                        //发送成功异步回调(非阻塞)
                        (metadata, exception) -> {
                            log.info("异步回调：主题->" + metadata.topic() + " 分区编号->" + metadata.partition() + "当前分区上的索引->" + metadata.offset());
                        }
                );
                //.get()发送成功同步回调(阻塞)
                TimeUnit.SECONDS.sleep(1);
                ++num;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new DszKafkaProducer("test-topic-89").start();
    }
}
