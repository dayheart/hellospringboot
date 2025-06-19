package com.dayheart.hello.kafka;

import java.io.IOException;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dayheart.hello.property.TierConfig;
import com.dayheart.tcp.TCPClient;
import com.dayheart.util.Utils;
import com.dayheart.util.XLog;

import kisb.sb.tmsg.SysHeader;

//@Service
@Component
public class KafkaConsumer {
	
	@Autowired
	private TierConfig tierConfig;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public KafkaConsumer() {
		
	}
	
	//@KafkaListener(topics = "#{systemEnvironment['DAYHEART.ROLE']}", groupId = "foo")
	@KafkaListener(topics = "#{tierConfig.getRole()}", groupId = "foo")
	public void consumeEsbMsg(String message) {
		XLog.stdout(String.format("Consumer topic: [%s]", tierConfig.getRole()));
		String flatjson = SysHeader.flatJson(message);
		XLog.stdout(String.format("FLAT JSON:[%s]", flatjson));
		
		byte[] sysHeader_b = SysHeader.toBytes(flatjson);
		Map<String, Object> sysHeader_m = SysHeader.toMap(sysHeader_b);
		XLog.stdout(String.format("MAP [%s]", sysHeader_m));
		
		executeRequest(sysHeader_m);
		
	}
	
	
	private void executeRequest(Map<String, Object> sysHeader) {
		String sysCd = tierConfig.getRole(); //sysCd(3) PRD, OFC, SAL, ORD, CST
		// SYNC | ASYNC 세팅, 이후 전문에서 지속... 최종 FEP 에서 사용
		String sync = "S";
		SysHeader.setTRMST(sysHeader, sysCd, "S", sync); // Send/Recv, Sync/Async
		
		String egress = tierConfig.getEgress(sysCd);
		//XLog.stdout(String.format("MCI_EGRESS [%s]", egress));
		String out = tierConfig.getOut(sysCd);
		XLog.stdout(String.format("SYS_CD[%s] EGRESS[%s]", sysCd, egress));
		if(egress!=null && egress.length()>0) {
			String[] outlets = egress.split(",");
			String url;
			String protocol;
			String host;
			int port;
			String uri;
			int i = 0;
			for(String outlet:outlets) {
				//XLog.stdout(String.format("EGRESS[%d]:%s", i++, outlet));
				switch(outlet) {
				/* 2025.03.10
				case "ESB" :
					SysHeader.setINFC(sysHeader, "OFFICES", "N", "", SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
					break;
				case "COR" :
					SysHeader.setINFC(sysHeader, "ORDERS", "N", "", SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
					break;
				case "FEP" :
					SysHeader.setINFC(sysHeader, "CUSTOMERS", "N", "", SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
					break;
				default :
					SysHeader.setINFC(sysHeader, "SALESREPS", "N", "", SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
				*/
				case "ESB" :
					SysHeader.setINFC(sysHeader, "OFFICES", "N", SysHeader.TMSG_APP_ID.getField(sysHeader), SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
					break;
				case "COR" :
					SysHeader.setINFC(sysHeader, "ORDERS", "N", SysHeader.TMSG_APP_ID.getField(sysHeader), SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
					break;
				case "FEP" :
					SysHeader.setINFC(sysHeader, "CUSTOMERS", "N", SysHeader.TMSG_APP_ID.getField(sysHeader), SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
					break;
				default :
					SysHeader.setINFC(sysHeader, "SALESREPS", "N", SysHeader.TMSG_APP_ID.getField(sysHeader), SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
				}
				
				protocol = tierConfig.getProtocol(outlet.toUpperCase());
				if(protocol == "kafka") {
					XLog.stdout(String.format("SKIP_MESSAGE_PROTOCOL[%s]", protocol));
					continue;
				}
				
				host = tierConfig.getHost(outlet.toUpperCase());
				port = tierConfig.getPort(outlet.toUpperCase());
				uri = tierConfig.getUri(outlet.toUpperCase());
				String[] uris = uri.split(",");
				
				int idx = Utils.getRandomNumber(0, (uris.length));
				uri = uris[idx];
				url = String.format("%s://%s:%d%s", protocol,host,port,uri);
				
				String responseStr = null;
				if(uri.endsWith("json")) {
					responseStr = TCPClient.executeJsonByApacheHttpClient(url, "POST", SysHeader.toJsonString(sysHeader));
				} else {
					responseStr = new String( TCPClient.executeBytesByApacheHttpClient(url, "POST", SysHeader.toBytes(sysHeader)));
				}
				
				XLog.stdout("KAFKA_OUT_URL: " + url);
				
			}
		}
	}
	/*
	
	@Bean
	public NewTopic topic1() {
		return TopicBuilder.name("topic1")
    			.partitions(10) // 10개의 파티션으로 분할
    			.replicas(1) // 1개의 레플리카로 복
    			.build();
    }

    @KafkaListener(topics = "topic1", groupId = "foo")
    public void consume1(String message) throws IOException {
    	//Thread.dumpStack();
    	System.out.println("Consumer topic: topic1, message: " + message);
    }
    
    @Bean
    public NewTopic topic2() {
    	return TopicBuilder.name("topic2")
    			.partitions(10)
    			.replicas(1)
    			.build();
    }
    
    @KafkaListener(topics = "topic2", groupId = "foo")
    public void consume2(String message) throws IOException {
    	System.out.println("Consumer topic: topic2, message: " + message);
    }
    
    
    @Bean
	public NewTopic esb_kafka_topic() {
		return TopicBuilder.name("esb_kafka_topic")
    			.partitions(10) // 10개의 파티션으로 분할
    			.replicas(1) // 1개의 레플리카로 복
    			.build();
    }
    
    @KafkaListener(topics = "esb_kafka_topic", groupId = "foo")
    public void consumeEsbMsg(String message) throws IOException {
    	//System.out.println("Consumer topic: esb-kafka-topic, message: " + message);
    	//XLog.stdout(String.format("Consumer topic: esb_kafka_topic, message: %s", message));
    	XLog.stdout(String.format("Consumer topic: esb_kafka_topic, message"));
    }
    */
}


/*
java.lang.Exception: Stack trace
	at java.base/java.lang.Thread.dumpStack(Thread.java:1389)
	at com.dayheart.hello.kafka.KafkaConsumer.consume1(KafkaConsumer.java:28)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:169)
	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:119)
	at org.springframework.kafka.listener.adapter.HandlerAdapter.invoke(HandlerAdapter.java:56)
	at org.springframework.kafka.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:376)
	at org.springframework.kafka.listener.adapter.RecordMessagingMessageListenerAdapter.onMessage(RecordMessagingMessageListenerAdapter.java:92)
	at org.springframework.kafka.listener.adapter.RecordMessagingMessageListenerAdapter.onMessage(RecordMessagingMessageListenerAdapter.java:53)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeOnMessage(KafkaMessageListenerContainer.java:2848)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeOnMessage(KafkaMessageListenerContainer.java:2826)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.lambda$doInvokeRecordListener$56(KafkaMessageListenerContainer.java:2744)
	at io.micrometer.observation.Observation.observe(Observation.java:565)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeRecordListener(KafkaMessageListenerContainer.java:2742)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeWithRecords(KafkaMessageListenerContainer.java:2595)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeRecordListener(KafkaMessageListenerContainer.java:2481)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeListener(KafkaMessageListenerContainer.java:2123)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeIfHaveRecords(KafkaMessageListenerContainer.java:1478)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.pollAndInvoke(KafkaMessageListenerContainer.java:1442)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.run(KafkaMessageListenerContainer.java:1313)
	at java.base/java.util.concurrent.CompletableFuture$AsyncRun.run(CompletableFuture.java:1804)
	at java.base/java.lang.Thread.run(Thread.java:840)
 */
