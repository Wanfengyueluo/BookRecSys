package com.wan.kafkastreaming;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

/**
 * @author wanfeng
 * @date 2021/3/13 18:44
 */
public class MyEventTimeExtractor implements TimestampExtractor {
	@Override
	public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
		return 0;
	}
}
