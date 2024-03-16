package com.dayheart.hello.web;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.HandlerMapping;

import com.dayheart.tcp.TCPClient;
import com.dayheart.util.XLog;
import com.inzent.igate.adapter.AdapterParameter;
import com.inzent.igate.connector.IGateConnectorException;
import com.inzent.igate.connector.socket.SocketConnector;
import com.inzent.igate.core.exception.IGateException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kisb.sb.tmsg.SysHeader;
import kisb.sb.tmsg.TelegramMessageUtil;

@Controller
public class MCIController {
	
	// SEE TierConfig
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
	
	//@Value("#{tier['COR.PROTOCOL']}")
	@Value("${COR.PROTOCOL}")
	private String corProtocol;
	
	//@Value("#{tier['COR.HOST']}")
	@Value("${COR.HOST}")
	private String corHost;
	
	//@Value("#{tier['COR.PORT']}")
	@Value("${COR.PORT}")
	private int corPort;
	
	//@Value("#{tier['COR.OUT']}")
	@Value("${COR.OUT}")
	private String corOut;
	
	public MCIController() {
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
		
		String coI = prop.getProperty("COR.PROTOCOL");
		if(coI!=null) {
			corProtocol = coI;
		}
		
		String coH = prop.getProperty("COR.HOST");
		if(coH!=null) {
			corHost = coH;
		} 
		
		String coP = prop.getProperty("COR.PORT");
		if(coP!=null) {
			try {
				corPort = Integer.parseInt(coP);
			}catch (NumberFormatException nfe) {
				// TODO: handle exception
				System.out.println(nfe);
			}
			
		}
				
		String coO = prop.getProperty("COR.OUT");
		if(coO!=null) {
			corOut = coO;
		}
	}
	
	@PostMapping({"/mci/igate", "/esb/tibco"})
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
				url = corProtocol + "://" + corHost + ":" + corPort + coreUri;
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
				if(mciOut.equalsIgnoreCase("octet-stream")) {
					b_request = tmsgUtil.setSendMessageInfo(req_sysHeader, "MCI", "S", SysHeader.TMSG_SYNCZ_SECD.getField(req_sysHeader).trim(), "_____MCI______TRANSACTION_REQUEST_MESSAGE_BODY_____ZZ".getBytes());
					String ALL_TMSG_LNTH = String.format("%08d", b_request.length);
					SysHeader.ALL_TMSG_LNTH.setField(ALL_TMSG_LNTH.getBytes(), b_request);
					
					Method m = adapterParameter.getClass().getMethod("setRequestData", new Class[] { byte[].class });
					m.invoke(adapterParameter, new Object[] { b_request });
				}
				
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
