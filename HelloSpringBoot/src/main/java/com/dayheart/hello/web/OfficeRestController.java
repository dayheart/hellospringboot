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

import com.dayheart.hello.jpa.Office;
import com.dayheart.hello.jpa.Product;
import com.dayheart.hello.jpa.ProductId;
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
public class OfficeRestController {

    @Autowired
    private JpaDaoIfOffice officeRepository;
    
    public OfficeRestController() {
		
	}
    
    @GetMapping("/api/offices")
    public List<Office> findAll() {
    	return officeRepository.findAll();
    }
    
    @GetMapping("/api/office")
    //public Product retrieveByProduct(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
    public ResponseEntity<Office> retrieveByOffice(@RequestHeader("office") int office) {    	
    	return new ResponseEntity<Office>(officeRepository.getReferenceById(office),HttpStatusCode.valueOf(200));
    }
}