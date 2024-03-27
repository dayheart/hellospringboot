package com.dayheart.hello.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
	public CompletableFuture<List<Product>> getProducts() throws InterruptedException {
		log.info("getProducts starts");
		RestTemplate restTemplate = new RestTemplate();
		List<Product> productsData = restTemplate.getForObject("http://localhost:8080/api/products", List.class);
		log.info("productsData, {}", productsData);
		Thread.sleep(1000L);
		log.info("getProducts completed");
		return CompletableFuture.completedFuture(productsData);
	}
	
	@Async
	public CompletableFuture<List<Office>> getOffices() throws InterruptedException {
		log.info("getOffices starts");
		RestTemplate restTemplate = new RestTemplate();
		List<Office> officesData = restTemplate.getForObject("http://localhost:8080/api/offices", List.class);
		log.info("officesData, {}", officesData);
		Thread.sleep(1000L);
		log.info("getOffices completed");
		return CompletableFuture.completedFuture(officesData);
	}
}
