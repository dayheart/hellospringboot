package com.dayheart.hello.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.dayheart.util.XLog;

@Service
//@RequiredArgsConstructor
public class KafkaProducer {
	
	@Autowired
	private final KafkaTemplate<String, String> kafkaTemplate;

	// Not support @RequiredArgsConstructor
	//@Autowired
	public KafkaProducer(KafkaTemplate kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	
	public void sendMessage1(String message) {
    	System.out.println("Producer topic: topic1, message: " + message);
        kafkaTemplate.send("topic1", message);
    }
    
    public void sendMessage2(String message) {
    	System.out.println("Producer topic: topic2, message: " + message);
        kafkaTemplate.send("topic2", message);
    }
    
    public void sendMessage(String message) {
    	//XLog.stdout(String.format("SEND esb_kafka_topic [%s]", message));
    	XLog.stdout(String.format("SEND esb_kafka_topic"));
    	kafkaTemplate.send("esb_kafka_topic", message);
    }

}
