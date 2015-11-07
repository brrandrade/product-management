package com.engyes.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * The Class MvcConfig set up the basic configuration for web servlet
 *
 * @author  Bruno Andrade
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
	 */
	@Override
	public void addViewControllers( ViewControllerRegistry registry ) {
		registry.addViewController( "/login" ).setViewName( "login" );
		registry.addViewController( "/" ).setViewName( "index" );
	}

}
