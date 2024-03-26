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

import com.dayheart.hello.jpa.Product;
import com.dayheart.hello.jpa.ProductId;
import com.dayheart.hello.jpa.repository.JpaDaoIfProduct;
import com.dayheart.tcp.TCPClient;

import kisb.sb.tmsg.SysHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@RestController
public class ProductRestController2 {

    @Autowired
    private JpaDaoIfProduct productRepository;
    
	@Value("${MCI.PROTOCOL}")
	private String mciProtocol;
	
	//@Value("#{tier['COR.HOST']}")
	@Value("${MCI.HOST}")
	private String mciHost;
	
	//@Value("#{tier['COR.PORT']}")
	@Value("${MCI.PORT}")
	private int mciPort;
	
	//@Value("#{tier['COR.OUT']}")
	@Value("${MCI.OUT}")
	private String mciOut;
	
	public ProductRestController2() {
		Properties prop = System.getProperties();
		String mciI = prop.getProperty("MCI.PROTOCOL");
		if(mciI!=null) {
			mciProtocol = mciI;
		}
		
		String mciH = prop.getProperty("MCI.HOST");
		if(mciH!=null) {
			mciHost = mciH;
		} 
		
		String mciP = prop.getProperty("MCI.PORT");
		if(mciP!=null) {
			try {
				mciPort = Integer.parseInt(mciP);
			}catch (NumberFormatException nfe) {
				// TODO: handle exception
				System.out.println(nfe);
			}
			
		}
				
		String mciO = prop.getProperty("MCI.OUT");
		if(mciO!=null) {
			mciOut = mciO;
		}
		
	}

    @GetMapping("/api/products/old")
    public List<Product> findAll() {
    	
    	return productRepository.findAll();
    }
    
    @GetMapping("/api/product/old")
    //public Product retrieveByProduct(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
    public ResponseEntity<Product> retrieveByProduct(@RequestHeader("mfr_id") String mfrId, @RequestHeader("product_id") String productId) {
    	
    	//System.out.println("retrieveByProduct");
    	ProductId pId = new ProductId(mfrId, productId);
    	//System.out.println(pId);
    	
    	
    	Map<String, Object> sysHeader = new HashMap<String, Object>();
    	String sysCd = "PRD"; //sysCd(3) PRD, OFC, SAL, ORD, CST
    	SysHeader.setGUID(sysHeader, sysCd, "P"); //  Prod, Dev, Test
		SysHeader.setDMND(sysHeader, sysCd); // ex) CHL=D19
		
		// SYNC | ASYNC 세팅, 이후 전문에서 지속... 최종 FEP 에서 사용
		String sync = "S";
		SysHeader.setTRMST(sysHeader, sysCd, "S", sync); // Send/Recv, Sync/Async
		
		SysHeader.setINFC(sysHeader, String.format("%s-%s", mfrId, productId), "N", "", String.format("%s-%s", mfrId, productId)); // INFC_ID(part), SVC_ID(eng)
		
		//System.out.println( Thread.currentThread().getStackTrace()[1] + " : " + "[" + new String( SysHeader.toBytes(header))  + "]");
		
		String url =  mciProtocol + "://" + mciHost + ":" + mciPort; 
		url += "/igate";
		
		String responseStr = null;
		//responseStr = TCPClient.executeJsonByApacheHttpClient(url, "POST", SysHeader.toJsonString(sysHeader));
    	
    	return new ResponseEntity<Product>(productRepository.getReferenceById(pId),HttpStatusCode.valueOf(200));
    }
    
    
    @GetMapping("/api/whole/old")
    public List<Product> whole() {
    	
    	return productRepository.findAll();
    }
    
}