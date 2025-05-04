package com.dayheart.hello.web;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import com.dayheart.hello.kafka.KafkaProducer;
import com.dayheart.hello.property.TierConfig;
import com.dayheart.tcp.TCPClient;
import com.dayheart.util.Utils;
import com.dayheart.util.XLog;
import com.inzent.igate.adapter.AdapterParameter;
import com.inzent.igate.connector.IGateConnectorException;
import com.inzent.igate.connector.socket.SocketConnector;
import com.inzent.igate.core.exception.IGateException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kisb.sb.tmsg.SysHeader;
import kisb.sb.tmsg.TelegramMessageUtil;

//@Controller
// org.thymeleaf.exceptions.TemplateInputException: Error resolving template [mci/json], template might not exist or might not be accessible by any of the configured Template Resolvers
@RestController
public class MCIController {
	
	@Autowired
	private TierConfig tierConfig;
	
	//@Value("#{tier['COR.HOST']}")
	
	// 2025.04.29 현대차증권 테스트
	@Autowired
	private KafkaProducer producer;
	

	public MCIController() {
	}
	
	//@GetMapping({"/mci/json"})
	@PostMapping({"/mci/json"})
	public void handleJsonRequest(@RequestBody Map<String, Object> sysHeader) {
		//XLog.stdout(String.format("MAP [%s]", sysHeader));
		
		executeRequest(sysHeader);
		//XLog.stdout(String.format("MAP [%s]", sysHeader));
	}
	
	//@GetMapping({"/mci/octet-stream"})
	@PostMapping({"/mci/octet-stream"})
	public void handleBytesRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] bytesHeader = TCPClient.retrieveBodyToBytes(request.getInputStream());
		//XLog.stdout(String.format("BYTES [%s]", new String(bytesHeader)));
		
		Map<String, Object> sysHeader = SysHeader.toMap(bytesHeader);
		
		executeRequest(sysHeader);
	}
	
	private void executeRequest(Map<String, Object> sysHeader) {
		String sysCd = "MCI"; //sysCd(3) PRD, OFC, SAL, ORD, CST
		// SYNC | ASYNC 세팅, 이후 전문에서 지속... 최종 FEP 에서 사용
		String sync = "S";
		SysHeader.setTRMST(sysHeader, sysCd, "S", sync); // Send/Recv, Sync/Async
		
		String egress = tierConfig.getEgress("MCI");
		//XLog.stdout(String.format("MCI_EGRESS [%s]", egress));
		String out = tierConfig.getOut("MCI");
		if(egress!=null && egress.length()>0) {
			String[] outlets = egress.split(","); // BE CAREFULL
			//XLog.stdout(String.format("MCI_OUTLETS [%s], len:%d", outlets, outlets.length));// [Ljava.lang.String;@7d26cead], len:1
			String url;
			String protocol;
			String host;
			int port;
			String uri;
			int i = 0;
			for(String outlet:outlets) {
				//XLog.stdout(String.format("EGRESS[%d]:%s", i++, outlet));
				switch(outlet) {
				/* 2025.03.10
				case "ESB" :
					SysHeader.setINFC(sysHeader, "OFFICES", "N", "", SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
					break;
				case "COR" :
					SysHeader.setINFC(sysHeader, "ORDERS", "N", "", SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
					break;
				case "FEP" :
					SysHeader.setINFC(sysHeader, "CUSTOMERS", "N", "", SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
					break;
				default :
					SysHeader.setINFC(sysHeader, "SALESREPS", "N", "", SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
				*/
				case "ESB" :
					SysHeader.setINFC(sysHeader, "OFFICES", "N", SysHeader.TMSG_APP_ID.getField(sysHeader), SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
					break;
				case "COR" :
					SysHeader.setINFC(sysHeader, "ORDERS", "N", SysHeader.TMSG_APP_ID.getField(sysHeader), SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
					break;
				case "FEP" :
					SysHeader.setINFC(sysHeader, "CUSTOMERS", "N", SysHeader.TMSG_APP_ID.getField(sysHeader), SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
					break;
				case "IGT" :
					SysHeader.setINFC(sysHeader, "PRODUCTS", "N", SysHeader.TMSG_APP_ID.getField(sysHeader), SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
					AdapterParameter adapterParameter = new AdapterParameter();
					adapterParameter.setRequestData(SysHeader.toBytes(sysHeader));
					
					host = tierConfig.getHost("IGT");
					port = tierConfig.getPort("IGT");
					
					XLog.stdout(String.format("IGT_IP [%s]", host));
					XLog.stdout(String.format("IGT_port [%d]", port));
					
					adapterParameter.put("serverIP", host);
					adapterParameter.put("port", port);
					
					SocketConnector connector = new SocketConnector();
					try {
						connector.callService(adapterParameter);
					} catch (IGateConnectorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default :
					SysHeader.setINFC(sysHeader, "SALESREPS", "N", SysHeader.TMSG_APP_ID.getField(sysHeader), SysHeader.TMSG_SVC_ID.getField(sysHeader)); // INFC_ID(part), SVC_ID(eng)
				}
				
				protocol = tierConfig.getProtocol(outlet.toUpperCase());
				
				if(protocol!=null && protocol.equals("http")) {
					host = tierConfig.getHost(outlet.toUpperCase());
					port = tierConfig.getPort(outlet.toUpperCase());
					uri = tierConfig.getUri(outlet.toUpperCase());
					String[] uris = uri.split(",");
					
					int idx = Utils.getRandomNumber(0, (uris.length));
					uri = uris[idx];
					url = String.format("%s://%s:%d%s", protocol,host,port,uri);
					
					String responseStr = null;
					
					if(uri.endsWith("json")) {
						responseStr = TCPClient.executeJsonByApacheHttpClient(url, "POST", SysHeader.toJsonString(sysHeader));
					} else {
						responseStr = new String( TCPClient.executeBytesByApacheHttpClient(url, "POST", SysHeader.toBytes(sysHeader)));
					}
					
					XLog.stdout("MCI_OUT_URL: " + url);
				} else if(protocol!=null && protocol.equals("kafka")) {
					producer.sendMessage(SysHeader.toJsonString(sysHeader));
				}
				
			}
		}
	}
	
	/*
	 * Invalid mapping pattern detected: /**\/mcin\/**
^
No more pattern data allowed after {*...} or ** pattern element

Action:

Fix this pattern in your application or switch to the legacy parser implementation with 'spring.mvc.pathmatch.matching-strategy=ant_path_matcher'.
	 */
	//@PostMapping({"/**"})
	@PostMapping({"/mci/old"})
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		//System.out.println(path);
		
		String contentType = request.getContentType();
		
		if(contentType!=null) {
			
			byte[] rcv_sysHeader = null;
			
			byte[] b_body = TCPClient.retrieveBodyToBytes(request.getInputStream());
			XLog.stdout(String.format("b_body[%s]", new String(b_body)));
			
			if( contentType.equalsIgnoreCase("application/json") ) {
				rcv_sysHeader = SysHeader.toBytes(SysHeader.flatJson(new String(b_body)));
				SysHeader.toBytesPretty(new String(b_body));
				
			} else if(contentType.equalsIgnoreCase("application/octet-stream")) {
				rcv_sysHeader = new byte[b_body.length];
				System.arraycopy(b_body, 0, rcv_sysHeader, 0, b_body.length);
				
				XLog.stdout("OCTET-STREAM [" + new String(rcv_sysHeader) + "], len:" + rcv_sysHeader.length);
			} else if(contentType.equalsIgnoreCase("application/xml")) {
				
			} else if(contentType.equalsIgnoreCase("text/html")) {
				
			} else {
				;
			}
			
			
			byte[] b_request = null; // 요청
			byte[] b_response = null; // 회신
			byte[] b_rcv; // 수신
			
			String url = null;
			TelegramMessageUtil tmsgUtil = TelegramMessageUtil.getInstance(false); // 싱글톤
			
			// for FEP FEP header 120byte + sys_header, ex) 120byte(anylink header)
			byte[] req_sysHeader = null;
			req_sysHeader = new byte[1057];
			//System.arraycopy(rcv_sysHeader, 0, req_sysHeader, 0, SysHeader.getLength());
			System.arraycopy(rcv_sysHeader, 0, req_sysHeader, 0, 1057);
						
			try {
				String coreUri = "/cor/core_tmax_fep.jsp";
				//url = corProtocol + "://" + corHost + ":" + corPort + coreUri;
				//url = corProtocol + "://" + esbHost + ":" + esbPort + request.getContextPath() + "/ESB";
				
				SocketConnector connector = null;
				AdapterParameter adapterParameter = new AdapterParameter();
			
				
				Field f = adapterParameter.getClass().getDeclaredField("data");
				f.setAccessible(true);
				
				Object obj = f.get(adapterParameter);
				if( obj instanceof java.util.HashMap ) {
					java.util.HashMap map = (java.util.HashMap)obj;
					map.put("request", request);
					map.put("response", response);
				}
				
				// 전송 전문 만들기.
				/*
				if(mciOut.equalsIgnoreCase("octet-stream")) {
					b_request = tmsgUtil.setSendMessageInfo(req_sysHeader, "MCI", "S", SysHeader.TMSG_SYNCZ_SECD.getField(req_sysHeader).trim(), "_____MCI______TRANSACTION_REQUEST_MESSAGE_BODY_____ZZ".getBytes());
					String ALL_TMSG_LNTH = String.format("%08d", b_request.length);
					SysHeader.ALL_TMSG_LNTH.setField(ALL_TMSG_LNTH.getBytes(), b_request);
					
					Method m = adapterParameter.getClass().getMethod("setRequestData", new Class[] { byte[].class });
					m.invoke(adapterParameter, new Object[] { b_request });
				}
				*/
				connector = new SocketConnector();
				connector.callService(adapterParameter);
			
			
			
				// HTTP 일 경우에...
				//b_rcv = tmsgUtil.transmit(url, "POST", b_request); // if !error
				
				
				b_response = new byte[b_request.length];
				System.arraycopy(b_request, 0, b_response, 0, b_request.length);
				
				// 송신 헤더 값 결정
				SysHeader.setTRMST(b_response, "MCI", "R", SysHeader.TMSG_SYNCZ_SECD.getField(b_request));
				// 에러 유무 회신
				SysHeader.setRSPNS(b_response, "0", "MCI", "", "");
	
				
				XLog.stdout("REQ[" + new String(b_request) + "], len:" + b_request.length);
				XLog.stdout("RES[" + new String(b_response) + "], len:" + b_response.length);
				
								
				/*
				Map<String, Object> rs_map = (Map<String, Object>)results.get(0); // 해당하는 단어가 없을 수도 있다.
				
										
				String jsonString = SysHeader.toJsonString(rs_map);
				
				XLog.stdout("MCI_RESPONSE[" + jsonString + "]");
				
				response.getOutputStream().write(jsonString.getBytes());
				response.getOutputStream().flush();
				*/
			
			} catch (Exception e) {
				String[] arr = {"apple", "kiwi", "grape", "banana"};
				e.printStackTrace();
				IGateException ex = new IGateConnectorException(null, e, "MCIERR", e.toString(), arr);
			}
		} // end of contentType
		
	}

}
