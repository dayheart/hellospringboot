package com.dayheart.hello.web;

import com.dayheart.hello.persistence.repo.Prop;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SimpleController {
	
	@Value("${spring.application.name}")
	String appName;
	
	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("appName", appName);
		
		return "home";
	}
	
	
	@GetMapping("/props")
	public String propsPage(Model model) {
		
		Properties properties = System.getProperties();
		
		Enumeration<?> names = properties.propertyNames();
		
		ArrayList<Prop> props = new ArrayList<Prop>();
		
		for(;names.hasMoreElements();) {
			String name = (String)names.nextElement();
			String value = properties.getProperty(name, "");
			
			Prop prop = new Prop(name, value);
			props.add(prop);
		}
		model.addAttribute("props", props);
		
		return "props";
	}
	
	
	@GetMapping("/jarcheck") 
	//public String jarcheckPage(@RequestParam(value = "reqName", required = false) String reqName, Model model) {
	public String jarcheckPage(@RequestParam(value = "reqName", defaultValue = "jakarta.servlet.http.HttpServlet") String reqName, Model model) {
	//public String jarcheckPage(@RequestParam(value = "reqName", defaultValue = "org.springframework.stereotype.Controller") String reqName, Model model) {
	
		
		reqName = reqName.trim();
		
		model.addAttribute("reqName", reqName);
		
		reqName = reqName.replace('.', '/').trim();
		reqName = "/" + reqName + ".class";
		
		java.net.URL classUrl = this.getClass().getResource(reqName);
		
		String result = (classUrl==null)?String.format("%s is not found", reqName):String.format("%s: [%s]", reqName, classUrl.getFile());
		
		model.addAttribute("result", result);
		
		return "jarcheck";
	}
}
