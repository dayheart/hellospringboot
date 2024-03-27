package com.dayheart.hello.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dayheart.hello.jpa.Office;
import com.dayheart.hello.jpa.Product;
import com.dayheart.hello.service.HelloAsyncService;

@RestController
public class HelloAsyncController {
	
	private static Logger log = LoggerFactory.getLogger(HelloAsyncController.class);
	
	@Autowired
	private HelloAsyncService helloAsyncService;
	
	@GetMapping("/api/helloAsync")
	public void helloAsync() throws InterruptedException, ExecutionException {
		log.info("helloAsync Starts");
		CompletableFuture<List<Product>> products = helloAsyncService.getProducts();
		CompletableFuture<List<Office>> offices = helloAsyncService.getOffices();
		
		// Wait until they are all done
		CompletableFuture.allOf(products, offices).join();
		
		log.info("Products [" + products.get().getClass().getName() + "]");
		log.info("Offices [" + offices.get() + "]");
	}
}
