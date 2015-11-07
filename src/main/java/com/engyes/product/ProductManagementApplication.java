package com.engyes.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The Class ProductManagementApplication will start the application enabling auto 
 * configuration for spring boot start tomcat and set up the prepared configurations 
 * for all dependencies of this project
 *
 * @author  Bruno Andrade
 */
@ComponentScan( "com.engyes.product" )
@Configuration
@EnableAutoConfiguration
class ProductManagementApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main( String[] args ) {
		SpringApplication.run( ProductManagementApplication.class, args );
	}
}
