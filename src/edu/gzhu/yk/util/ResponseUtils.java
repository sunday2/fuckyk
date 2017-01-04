package edu.gzhu.yk.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;


public class ResponseUtils {

	//写入response
	public static void render(HttpServletResponse response,String contentType,String text){
		response.setContentType(contentType);
		try {
			response.getWriter().write(text);
			response.getWriter().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//发送的是JSON
	public static void renderJson(HttpServletResponse response,String text){
		render(response, "application/json;charset=UTF-8", text);
	}
	//发送的是xml
	public static void renderXml(HttpServletResponse response,String text){
		render(response, "text/xml;charset=UTF-8", text);
	}
	//发送到是text
	public static void renderText(HttpServletResponse response,String text){
		render(response, "text/plain;charset=UTF-8", text);
	}
	
	
}
