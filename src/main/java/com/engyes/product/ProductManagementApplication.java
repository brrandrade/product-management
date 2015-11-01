package com.engyes.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan( "com.engyes.product" )
@Configuration
@EnableAutoConfiguration
public class ProductManagementApplication {

	public static void main( String[] args ) {
		SpringApplication.run( ProductManagementApplication.class, args );
	}
}
