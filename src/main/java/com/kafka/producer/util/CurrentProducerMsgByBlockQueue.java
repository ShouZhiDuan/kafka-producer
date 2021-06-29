package com.kafka.producer.util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.util.PropertiesUtil;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Auther: ShouZhi@Duan
 * @Description: 模拟多线程发送消息
 */
public class CurrentProducerMsgByBlockQueue {
    //Kafka配置文件
    public static final String TOPIC_NAME = "test";
    public static final String KAFKA_PRODUCER = "kafka-producer.properties";
    public static final int producerNum=50;//实例池大小
    //阻塞队列实现生产者实例池,获取连接作出队操作，归还连接作入队操作
    public static BlockingQueue<KafkaProducer<String, String>> queue=new LinkedBlockingQueue<>(producerNum);
    //初始化producer实例池
    static {
        Properties properties=new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.10.33:9092,192.168.10.34:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"dsz-producer");
        //properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.kafka.producer.partition.MyPartition");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        for (int i = 0; i <producerNum ; i++) {
            KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
            queue.add(kafkaProducer);
        }
    }
    //生产者发送线程
    static class SendThread extends Thread {
        String msg;
        public SendThread(String msg){
            this.msg=msg;
        }
        public void run(){
            ProducerRecord record = new ProducerRecord(TOPIC_NAME, msg);
            try {
                KafkaProducer<String, String> kafkaProducer =queue.take();//从实例池获取连接,没有空闲连接则阻塞等待
                kafkaProducer.send(record);
                queue.put(kafkaProducer);//归还kafka连接到连接池队列
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //测试
    public static void main(String[]args){
        for (int i = 0; i <100 ; i++) {
            SendThread sendThread=new SendThread("test multi-thread producer!");
            sendThread.start();
        }
    }
}
