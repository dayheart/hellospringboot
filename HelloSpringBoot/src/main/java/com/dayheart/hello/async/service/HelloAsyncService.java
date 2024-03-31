package com.dayheart.hello.async.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dayheart.hello.jpa.Office;
import com.dayheart.hello.jpa.Product;

@Service
public class HelloAsyncService {
	private static Logger log = LoggerFactory.getLogger(HelloAsyncService.class);
	
	/*@Autowired
	private RestTemplate restTemplate;
	
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	*/
	/*
	 * 
***************************
APPLICATION FAILED TO START
***************************

Description:

The dependencies of some of the beans in the application context form a cycle:

   helloAsyncController (field private com.dayheart.hello.service.HelloAsyncService com.dayheart.hello.controller.HelloAsyncController.helloAsyncService)
┌─────┐
|  helloAsyncService (field private org.springframework.web.client.RestTemplate com.dayheart.hello.service.HelloAsyncService.restTemplate)
└─────┘


Action:

Relying upon circular references is discouraged and they are prohibited by default. Update your application to remove the dependency cycle between beans. As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.


	 */
	@Async
	public CompletableFuture<Product[]> getProducts() throws InterruptedException {
		log.info("getProducts starts");
		RestTemplate restTemplate = new RestTemplate();
		Product[] productsData = restTemplate.getForObject("http://localhost:8080/api/products", Product[].class);
		log.info("productsData, {}", productsData);
		Thread.sleep(1000L);
		log.info("getProducts completed");
		return CompletableFuture.completedFuture(productsData);
	}
	
	@Async
	public CompletableFuture<List<Object>> getOffices() throws InterruptedException {
		log.info("getOffices starts");
		RestTemplate restTemplate = new RestTemplate();
		List<Object> officesData = restTemplate.getForObject("http://localhost:8080/api/offices", List.class);
		log.info("officesData, {}", officesData);
		Thread.sleep(1000L);
		log.info("getOffices completed");
		return CompletableFuture.completedFuture(officesData);
	}
	
	@Async
	public CompletableFuture<Object> getOffice(int office) throws InterruptedException {
		log.info("getOffice starts");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("office", Integer.toString(office));
		
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<Object> responseEntity = restTemplate.exchange("http://localhost:8080/api/office", HttpMethod.GET, entity, Object.class);
		
		log.info("officeData, {}", responseEntity);
		Thread.sleep(1000L);
		log.info("getOffice completed");
		
		return CompletableFuture.completedFuture(responseEntity.getBody());
		// Office [{office=22, city=Denver, region=Western, mgr=108, target=300000.0, sales=186042.0, hibernateLazyInitializer={}}]
	}
}
