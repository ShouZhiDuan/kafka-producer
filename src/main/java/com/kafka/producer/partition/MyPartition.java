package com.kafka.producer.partition;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 */
@Slf4j
public class MyPartition implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] bytes, Object value, byte[] bytes1, Cluster cluster) {
        //当前主题
        log.info("当前主题：" + topic);
        //当前主题的key
        log.info("当前主题的key为：" + key.toString());
        //当前主题的value
        log.info("当前主题的value为：" + value.toString());
        //获取当前主题有多少个分区
        Integer count = cluster.partitionCountForTopic(topic);
        log.info("当前主题{},共有{}个分区。",topic,count);
        //获取当前主题详情列表
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        if(null == key){
            Random random = new Random();
            return random.nextInt(count);
        }
        return Math.abs(key.hashCode())%count;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
