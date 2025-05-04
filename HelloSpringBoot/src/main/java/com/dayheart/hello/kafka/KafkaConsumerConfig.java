package com.dayheart.hello.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.dayheart.hello.property.TierConfig;
import com.dayheart.util.XLog;

//import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
/*
Caused by: org.apache.kafka.common.KafkaException: class com.fasterxml.jackson.databind.deser.std.StringDeserializer is not an instance of org.apache.kafka.common.serialization.Deserializer
at org.apache.kafka.common.config.AbstractConfig.getConfiguredInstance(AbstractConfig.java:400) ~[kafka-clients-3.6.1.jar:na]
at org.apache.kafka.common.config.AbstractConfig.getConfiguredInstance(AbstractConfig.java:434) ~[kafka-clients-3.6.1.jar:na]
at org.apache.kafka.common.config.AbstractConfig.getConfiguredInstance(AbstractConfig.java:419) ~[kafka-clients-3.6.1.jar:na]
at org.apache.kafka.clients.consumer.internals.ConsumerUtils.createKeyDeserializer(ConsumerUtils.java:155) ~[kafka-clients-3.6.1.jar:na]
at org.apache.kafka.clients.consumer.KafkaConsumer.<init>(KafkaConsumer.java:701) ~[kafka-clients-3.6.1.jar:na]
... 33 common frames omitted
*/

// implementation 'org.springframework.kafka:spring-kafka'
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	// application.properties
	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String bootstrapAddress;
	
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;
	
	@Value("${spring.kafka.consumer.auto-offset-reset}")
	private String autoOffsetReset;
	
	@Value("${DAYHEART.ROLE}")
	private String tireRole;
	
	@Autowired
	private TierConfig tierConfig;
	
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		
		XLog.stdout(String.format("DAYHEART.ROLE [%s]", tireRole));
		
		String protocol = tierConfig.getProtocol(tireRole.toUpperCase());
		
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		
		return new DefaultKafkaConsumerFactory<>(configs);
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

}

