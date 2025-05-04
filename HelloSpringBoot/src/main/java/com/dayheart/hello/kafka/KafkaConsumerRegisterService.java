package com.dayheart.hello.kafka;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.dayheart.util.XLog;

@Service
public class KafkaConsumerRegisterService {

	//@Autowired
	//private ApplicationContext applicationContext;
	
	public KafkaConsumerRegisterService(ApplicationContext applicationContext) {
		ConfigurableApplicationContext caCtx = (ConfigurableApplicationContext)applicationContext;
		
		
		XLog.stdout(String.format("ConfigurableApplicationContext [%s]", caCtx));
		ConfigurableListableBeanFactory beanFactory = caCtx.getBeanFactory();
		
		XLog.stdout(String.format("FIND SYS PROPERTY DAYHEART.ROLE [%s]", System.getProperty("DAYHEART.ROLE")));
	}

}
