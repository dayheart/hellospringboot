package com.dayheart.hello.property;

import java.lang.reflect.Field;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import com.dayheart.util.XLog;

@Configuration
@PropertySource("classpath:/config/tier.properties")
/*
 * <util:properties id="tier" location="classpath:/config/tier.properties" />
 */
public class TierConfig {
	@Value("${MCI.PROTOCOL}")
	private String mciProtocol;
	
	@Value("${MCI.HOST}")
	private String mciHost;
	
	@Value("${MCI.PORT}")
	private int mciPort;
	
	@Value("${MCI.URI}")
	private String mciUri;
	private String[] mciUris = null;
	
	@Value("${MCI.OUT}")
	private String mciOut;
	
	@Value("${MCI.EGRESS}")
	private String mciEgress;

	
	@Value("${ESB.PROTOCOL}")
	private String esbProtocol;
	
	@Value("${ESB.HOST}")
	private String esbHost;
	
	@Value("${ESB.PORT}")
	private int esbPort;
	
	@Value("${ESB.URI}")
	private String esbUri;
	private String[] esbUris = null;
	
	@Value("${ESB.OUT}")
	private String esbOut;
	
	@Value("${ESB.EGRESS}")
	private String esbEgress;
	
	
	@Value("${COR.PROTOCOL}")
	private String corProtocol;
	
	@Value("${COR.HOST}")
	private String corHost;
	
	@Value("${COR.PORT}")
	private int corPort;
	
	@Value("${COR.URI}")
	private String corUri;
	private String[] corUris = null;
	
	@Value("${COR.OUT}")
	private String corOut;
	
	@Value("${COR.EGRESS}")
	private String corEgress;

	
	@Value("${EAI.PROTOCOL}")
	private String eaiProtocol;
	
	@Value("${EAI.HOST}")
	private String eaiHost;
	
	@Value("${EAI.PORT}")
	private int eaiPort;
	
	@Value("${EAI.URI}")
	private String eaiUri;
	private String[] eaiUris = null;
	
	@Value("${EAI.OUT}")
	private String eaiOut;
	
	@Value("${EAI.EGRESS}")
	private String eaiEgress;
	
	
	@Value("${FEP.PROTOCOL}")
	private String fepProtocol;
	
	@Value("${FEP.HOST}")
	private String fepHost;
	
	@Value("${FEP.PORT}")
	private int fepPort;
	
	@Value("${FEP.URI}")
	private String fepUri;
	private String[] fepUris = null;
	
	@Value("${FEP.OUT}")
	private String fepOut;
	
	@Value("${FEP.EGRESS}")
	private String fepEgress;
	
	@Value("${API.PROTOCOL}")
	private String apiProtocol;
	
	@Value("${API.HOST}")
	private String apiHost;
	
	@Value("${API.PORT}")
	private int apiPort;
	
	@Value("${API.URI}")
	private String apiUri;
	private String[] apiUris = null;
	
	@Value("${API.OUT}")
	private String apiOut;
	
	@Value("${API.EGRESS}")
	private String apiEgress;
	
	@Value("${IGT.PROTOCOL}")
	private String igtProtocol;
	
	@Value("${IGT.HOST}")
	private String igtHost;
	
	@Value("${IGT.PORT}")
	private int igtPort;
	
	@Value("${IGT.URI}")
	private String igtUri;
	private String[] igtUris = null;
	
	@Value("${IGT.OUT}")
	private String igtOut;
	
	@Value("${IGT.EGRESS}")
	private String igtEgress;
	
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	/*
	 * java.lang.Exception: Stack trace
	at java.base/java.lang.Thread.dumpStack(Thread.java:1389)
	at com.dayheart.hello.property.TierConfig.tierProperties(TierConfig.java:151)
	at com.dayheart.hello.property.TierConfig$$SpringCGLIB$$0.CGLIB$tierProperties$3(<generated>)
	at com.dayheart.hello.property.TierConfig$$SpringCGLIB$$FastClass$$1.invoke(<generated>)
	at org.springframework.cglib.proxy.MethodProxy.invokeSuper(MethodProxy.java:258)
	at org.springframework.context.annotation.ConfigurationClassEnhancer$BeanMethodInterceptor.intercept(ConfigurationClassEnhancer.java:331)
	at com.dayheart.hello.property.TierConfig$$SpringCGLIB$$0.tierProperties(<generated>)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:140)
	at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:651)
	at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:489)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1334)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1164)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:561)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521)
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:325)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:323)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:199)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:969)
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:960)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:625)
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146)
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:762)
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:464)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:334)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1358)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1347)
	at com.dayheart.hello.HelloSpringBootApplication.main(HelloSpringBootApplication.java:15)
	 */
	@Bean
	public PropertiesFactoryBean tierProperties() {
		PropertiesFactoryBean bean = new PropertiesFactoryBean();
		bean.setFileEncoding("UTF-8");
		bean.setLocation(new ClassPathResource("config/tier.properties"));
		//Thread.dumpStack();
		//XLog.stdout(String.format("PROPERTY_CONFIG [%s]", bean));
		// PROPERTY_CONFIG [org.springframework.beans.factory.config.PropertiesFactoryBean@579b1d86]
		return bean;
	}
	
	private Properties props = System.getProperties();
	
	public String getProtocol(String TIER) {
		String tiervalue = null;
		switch(TIER) {
		case "MCI" : 
			tiervalue = this.mciProtocol;
			break;
		case "ESB" :
			tiervalue = this.esbProtocol;
			break;
		case "COR" :
			tiervalue = this.corProtocol;
			break;
		case "EAI" :
			tiervalue = this.eaiProtocol;
			break;
		case "API" :
			tiervalue = this.apiProtocol;
			break;
		case "FEP" :
			tiervalue = this.fepProtocol;
			break;
		case "IGT" :
			tiervalue = this.igtProtocol;
			break;
		default :
			tiervalue = this.mciProtocol;
			break;
		
		}
		return props.getProperty(TIER.toUpperCase()+".POROTOCOL", tiervalue);
	}
	
	public String getHost(String TIER) {
		String tiervalue = null;
		switch(TIER) {
		case "MCI" : 
			tiervalue = this.mciHost;
			break;
		case "ESB" :
			tiervalue = this.esbHost;
			break;
		case "COR" :
			tiervalue = this.corHost;
			break;
		case "EAI" :
			tiervalue = this.eaiHost;
			break;
		case "API" :
			tiervalue = this.apiHost;
			break;
		case "FEP" :
			tiervalue = this.fepHost;
			break;
		case "IGT" :
			tiervalue = this.igtHost;
			break;
		default :
			tiervalue = this.mciHost;
			break;
		
		}
		return props.getProperty(TIER.toUpperCase()+".HOST", tiervalue);
	}
	
	public int getPort(String TIER) {
		int tiervalue = 0;
		switch(TIER) {
		case "MCI" : 
			tiervalue = this.mciPort;
			break;
		case "ESB" :
			tiervalue = this.esbPort;
			break;
		case "COR" :
			tiervalue = this.corPort;
			break;
		case "EAI" :
			tiervalue = this.eaiPort;
			break;
		case "API" :
			tiervalue = this.apiPort;
			break;
		case "FEP" :
			tiervalue = this.fepPort;
			break;
		case "IGT" :
			tiervalue = this.igtPort;
			break;
		default :
			tiervalue = this.mciPort;
			break;
		
		}
		String port = props.getProperty(TIER.toUpperCase()+".PORT");
		
		if(port!=null) {
			try {
				return Integer.parseInt(port);
			}catch (NumberFormatException nfe) {
				// TODO: handle exception
				System.out.println(nfe);
				return tiervalue;
			}
		} 
		return tiervalue;
	}
	
	public String getUri(String TIER) {
		String tiervalue = null;
		switch(TIER) {
		case "MCI" : 
			tiervalue = this.mciUri;
			break;
		case "ESB" :
			tiervalue = this.esbUri;
			break;
		case "COR" :
			tiervalue = this.corUri;
			break;
		case "EAI" :
			tiervalue = this.eaiUri;
			break;
		case "API" :
			tiervalue = this.apiUri;
			break;
		case "FEP" :
			tiervalue = this.fepUri;
			break;
		case "IGT" :
			tiervalue = this.igtUri;
			break;
		default :
			tiervalue = this.mciUri;
			break;
		
		}
		return props.getProperty(TIER.toUpperCase()+".URI", tiervalue);
	}
	
	
	public String[] getUris(String TIER) {
		
		String uri = this.getUri(TIER.toUpperCase());
		
		if(uri.indexOf(",")>-1) {
			return uri.split(",");
		} else if(uri!=null) {
			return new String[] { uri };
		}
		
		return null;
	}
	
	public String getOut(String TIER) {
		
		String tiervalue = null;
		switch(TIER) {
		case "MCI" : 
			tiervalue = this.mciOut;
			break;
		case "ESB" :
			tiervalue = this.esbOut;
			break;
		case "COR" :
			tiervalue = this.corOut;
			break;
		case "EAI" :
			tiervalue = this.eaiOut;
			break;
		case "API" :
			tiervalue = this.apiOut;
			break;
		case "FEP" :
			tiervalue = this.fepOut;
			break;
		case "IGT" :
			tiervalue = this.igtOut;
			break;
		default :
			tiervalue = this.mciOut;
			break;
		
		}
		return props.getProperty(TIER.toUpperCase()+".OUT", tiervalue);
	}
	
	public String getEgress(String TIER) {
		
		String tiervalue = null;
		switch(TIER) {
		case "MCI" : 
			tiervalue = this.mciEgress;
			break;
		case "ESB" :
			tiervalue = this.esbEgress;
			break;
		case "COR" :
			tiervalue = this.corEgress;
			break;
		case "EAI" :
			tiervalue = this.eaiEgress;
			break;
		case "API" :
			tiervalue = this.apiEgress;
			break;
		case "FEP" :
			tiervalue = this.fepEgress;
			break;
		case "IGT" :
			tiervalue = this.igtEgress;
			break;
		default :
			tiervalue = this.mciEgress;
			break;
		
		}
		return props.getProperty(TIER.toUpperCase()+".EGRESS", tiervalue);
		/*
		TIER = TIER.toLowerCase();
		
		XLog.stdout(String.format("MCI_EGRESS[%s]", (TIER + "Egress")));
		XLog.stdout(String.format("MCI_EGRESS_THIS[%s]", this)); 
		// com.dayheart.hello.property.TierConfig$$SpringCGLIB$$0@2e2c9e24
		try {
			Field field = this.getClass().getDeclaredField(TIER + "Egress");
			XLog.stdout(String.format("MCI_EGRESS_THIS[%s]", this));
			XLog.stdout(String.format("MCI_EGRESS_FIELD[%s]", field));
			
			field.setAccessible(true);
			Object out  = field.get(this);
			
			return props.getProperty(TIER.toUpperCase()+".EGRESS", (String)out);
			
		} catch (NoSuchFieldException e) {
			System.out.println(e.toString());
		} catch (IllegalAccessException illegalEx) {
			System.out.println(illegalEx.toString());
		}
		
		return null;
		*/
	}
}
