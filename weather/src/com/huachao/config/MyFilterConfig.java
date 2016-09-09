package com.huachao.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
/*
 * 实现WebApplicationInitializer接口，可以添加额外的serclet和Filter
 */
public class MyFilterConfig implements WebApplicationInitializer {
	/*@Override  //添加额外的servlet
	public void onStartup(ServletContext servletContext) throws ServletException {
	    Dynamic myservlet=servletContext.addServlet("myServlet", myServlet.class);
	    myservlet.addMapping("/custom/");
	}*/  
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		CharacterEncodingFilter filter=new CharacterEncodingFilter();
		filter.setEncoding("utf-8");
		javax.servlet.FilterRegistration.Dynamic myFilter=servletContext.addFilter("myFilter", filter);
		myFilter.addMappingForUrlPatterns(null, false, "/*");
	}

}
