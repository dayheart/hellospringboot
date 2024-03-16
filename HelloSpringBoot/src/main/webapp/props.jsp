<%@page import="javax.xml.transform.TransformerFactory"%>
<%@page import="javax.xml.parsers.SAXParserFactory"%>
<%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Properties"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JVM Properties</title>
</head>
<table border="1">
	<tr>
		<th>name</th><th>value</th>
	</tr>
<%
	Properties props = System.getProperties();
	//Enumeration<String> names = (String<String>)props.propertyNames(); 
	
	Enumeration names = props.propertyNames();
	for(;names.hasMoreElements();)
	{
		out.println("<tr>");
		
		String name = (String)names.nextElement();
		String value = props.getProperty(name);
		out.println("<td>");
		out.println(name);
		out.println("</td>");
		out.println("<td>");
		out.println(value);
		out.println("</td>");
		out.println("</tr>");
	}
	
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	out.println(dbf.getClass().getName());
	
	SAXParserFactory sf = SAXParserFactory.newInstance();
	out.println(sf.getClass().getName());
	
	TransformerFactory tf = TransformerFactory.newInstance();
	out.println(tf.getClass().getName());
%>
</table>
</body>
</html>