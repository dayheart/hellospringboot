package com.dayheart.hello.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProductController {
	
	@GetMapping("/chl/products")
	public String homePage(HttpServletRequest request, Model model) {
		
		String contextPath = request.getContextPath();
		System.out.println(String.format("CONTEXT_PATH[%s]", contextPath));
		model.addAttribute("context_path", contextPath);
		return "hello_products";
	}
	
}
