package com.dayheart.hello.property;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@PropertySource("classpath:/config/tier.properties")
/*
 * <util:properties id="tier" location="classpath:/config/tier.properties" />
 */
public class TierConfig {
	@Value("${COR.PROTOCOL}")
	private String corProtocol;
	
	@Value("${COR.HOST}")
	private String corHost;
	
	@Value("${COR.PORT}")
	private String corPort;
	
	@Value("${COR.OUT}")
	private String corOut;
	
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public PropertiesFactoryBean tierProperties() {
		PropertiesFactoryBean bean = new PropertiesFactoryBean();
		bean.setFileEncoding("UTF-8");
		bean.setLocation(new ClassPathResource("config/tier.properties"));
		
		return bean;
	}
}
