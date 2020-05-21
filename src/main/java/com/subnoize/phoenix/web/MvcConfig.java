package com.subnoize.phoenix.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * @author ericb
 *
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
	}

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/administration/home").setViewName("/administration/home");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/splash").setViewName("splash");
		registry.addViewController("/administration/admins").setViewName("/administration/admins");
		registry.addViewController("/administration/users").setViewName("/administration/users");
		registry.addViewController("/services/users").setViewName("/services/users");
	}

}
