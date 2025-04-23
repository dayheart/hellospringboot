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
import com.dayheart.hello.property.TierConfig;
import com.dayheart.tcp.TCPClient;
import com.dayheart.util.Utils;
import com.dayheart.util.XLog;

import kisb.sb.tmsg.SysHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@RestController
public class ProductRestController {

    @Autowired
    private JpaDaoIfProduct productRepository;
    
    @Autowired
    private TierConfig tierConf;
    
	public ProductRestController() {
		
	}

    @GetMapping("/api/products")
    public List<Product> findAll() {
    	
    	return productRepository.findAll();
    }
    
    @GetMapping("/api/product")
    //public Product retrieveByProduct(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
    // for nginx underscores_in_headers off;
    public ResponseEntity<Product> retrieveByProduct(@RequestHeader("mfr-id") String mfrId, @RequestHeader("product-id") String productId) {
    	/*
    	 * 	at java.base/java.lang.Thread.dumpStack(Thread.java:1389)
	at com.dayheart.hello.web.ProductRestController.retrieveByProduct(ProductRestController.java:52)
	at jdk.internal.reflect.GeneratedMethodAccessor29.invoke(Unknown Source)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:262)
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:190)
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118)
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:917)
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:829)
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
    	 */
    	ProductId pId = new ProductId(mfrId, productId);
    	//XLog.stdout(String.format("PRODUCT_ID [%s]", pId));
    	
    	
    	Map<String, Object> sysHeader = new HashMap<String, Object>();
    	String sysCd = "PRD"; //sysCd(3) PRD, OFC, SAL, ORD, CST
    	SysHeader.setGUID(sysHeader, sysCd, "P"); //  Prod, Dev, Test
		SysHeader.setDMND(sysHeader, sysCd); // ex) CHL=D19
		
		// SYNC | ASYNC 세팅, 이후 전문에서 지속... 최종 FEP 에서 사용
		String sync = "S";
		SysHeader.setTRMST(sysHeader, sysCd, "S", sync); // Send/Recv, Sync/Async
		
		//SysHeader.setINFC(sysHeader, String.format("%s-%s", mfrId, productId), "N", "", String.format("%s-%s", mfrId, productId)); // INFC_ID(part), SVC_ID(eng)
		//SysHeader.setINFC(sysHeader, "OFFICES", "N", "", String.format("%s-%s", mfrId, productId)); // INFC_ID(part), SVC_ID(eng)
		// 2025.03.10 changing for Tmax5_tcpgw_outbound
		SysHeader.setINFC(sysHeader, "OFFICES", "N", mfrId, productId);
		
		//System.out.println( Thread.currentThread().getStackTrace()[1] + " : " + "[" + new String( SysHeader.toBytes(header))  + "]");
		String url = null;
		String responseStr = null;
		if(tierConf.getProtocol("MCI").equalsIgnoreCase("http") || tierConf.getProtocol("MCI").equalsIgnoreCase("https")) {
			url = tierConf.getProtocol("MCI") + "://" + tierConf.getHost("MCI") + ":" + tierConf.getPort("MCI");
			
			if(tierConf.getUris("MCI")!=null) {
				int i = Utils.getRandomNumber(0, (tierConf.getUris("MCI").length));
				String[] uris = tierConf.getUris("MCI");
				url += uris[i];
				
				String uri = uris[i];
				//XLog.stdout("MCI_URI: " + url);
				
				if(uri.endsWith("json")) {
					responseStr = TCPClient.executeJsonByApacheHttpClient(url, "POST", SysHeader.toJsonString(sysHeader));
				} else {
					responseStr = new String( TCPClient.executeBytesByApacheHttpClient(url, "POST", SysHeader.toBytes(sysHeader)));
				}
				
			} else {
				url += tierConf.getUri("MCI");
				
				responseStr = new String( TCPClient.executeBytesByApacheHttpClient(url, "POST", SysHeader.toBytes(sysHeader)));
			}
		}
		XLog.stdout(String.format("%s-%s", mfrId, productId));    	
    	return new ResponseEntity<Product>(productRepository.getReferenceById(pId),HttpStatusCode.valueOf(200));
    }
    
    
    @GetMapping("/api/whole")
    public List<Product> whole() {
    	
    	return productRepository.findAll();
    }
    
}