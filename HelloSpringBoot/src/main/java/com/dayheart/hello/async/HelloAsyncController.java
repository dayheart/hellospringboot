package com.dayheart.hello.async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dayheart.hello.async.service.HelloAsyncService;
import com.dayheart.hello.jpa.Office;
import com.dayheart.hello.jpa.Product;

@RestController
public class HelloAsyncController {
	
	private static Logger log = LoggerFactory.getLogger(HelloAsyncController.class);
	
	@Autowired
	private HelloAsyncService helloAsyncService;
	
	@GetMapping("/api/helloAsync")
	public void helloAsync() throws InterruptedException, ExecutionException {
		log.info("helloAsync Starts");
		CompletableFuture<Product[]> products = helloAsyncService.getProducts();
		CompletableFuture<List<Object>> offices = helloAsyncService.getOffices();
		CompletableFuture<Object> office = helloAsyncService.getOffice(22);
		
		// Wait until they are all done
		CompletableFuture.allOf(products, offices, office).join();
		
		log.info("Products [" + products.get() + "]");
		log.info("Offices [" + offices.get() + "]");
		log.info("Office [" + office.get() + "]");
		
		Product[] productArray = products.get();
		
		/*
		for(Product p : productArray) {
			log.info(String.format("PRODUCT [%s]", p));
		}
		*/
	}
}
