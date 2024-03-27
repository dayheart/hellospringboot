package com.dayheart.hello.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dayheart.hello.jpa.Customer;
import com.dayheart.hello.jpa.Office;
import com.dayheart.hello.jpa.Product;
import com.dayheart.hello.jpa.ProductId;
import com.dayheart.hello.jpa.Salesrep;
import com.dayheart.hello.jpa.repository.JpaDaoIfSalesrep;
import com.dayheart.hello.jpa.repository.JpaDaoIfOffice;
import com.dayheart.tcp.TCPClient;
import com.dayheart.util.Utils;
import com.dayheart.util.XLog;

import kisb.sb.tmsg.SysHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@RestController
public class SalesrepRestController {

    @Autowired
    private JpaDaoIfSalesrep salesrepRepository;
    
    public SalesrepRestController() {
		
	}
    
    @GetMapping("/api/salesreps")
    public List<Salesrep> findAll() {
    	return salesrepRepository.findAll();
    }
    
    @GetMapping("/api/salesrep")
    //public Product retrieveByProduct(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
    public ResponseEntity<Salesrep> retrieveByOffice(@RequestHeader("empl_num") int emplNum) {    	
    	return new ResponseEntity<Salesrep>(salesrepRepository.getReferenceById(emplNum),HttpStatusCode.valueOf(200));
    }
}