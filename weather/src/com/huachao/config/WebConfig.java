package com.huachao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan("com.huachao.controller")      //扫描
@EnableWebMvc   //启动SpringMVC
public class WebConfig {
    /**
     * 配置视图解析器
     */
    @Bean                     
    public ViewResolver viewResolver(){
    	InternalResourceViewResolver resolver=new InternalResourceViewResolver();
    	resolver.setPrefix("/WEB-INF/views/");
    	resolver.setSuffix(".jsp");
    	return resolver;
    }
    
}
