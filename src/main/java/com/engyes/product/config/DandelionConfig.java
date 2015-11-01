package com.engyes.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.github.dandelion.core.web.DandelionFilter;
import com.github.dandelion.core.web.DandelionServlet;
import com.github.dandelion.datatables.core.web.filter.DatatablesFilter;
import com.github.dandelion.datatables.thymeleaf.dialect.DataTablesDialect;
import com.github.dandelion.thymeleaf.dialect.DandelionDialect;

@Component
@Configuration
public class DandelionConfig {

	@Bean
	public DataTablesDialect dataTablesDialect() {
		return new DataTablesDialect();
	}

	@Bean
	public DandelionDialect dandelionDialect() {
		return new DandelionDialect();
	}

	@Bean
	public FilterRegistrationBean dandelionFilterRegistrationBean(
			@Value( "${dandelion.profile.active}" ) String profile) {
		System.setProperty( "dandelion.profile.active", profile );
		return new FilterRegistrationBean( new DandelionFilter() );
	}

	@Bean
	public FilterRegistrationBean datatableFilterRegistrationBean() {
		return new FilterRegistrationBean( new DatatablesFilter() );
	}

	@Bean
	public ServletRegistrationBean dandelionServletRegistrationBean() {
		return new ServletRegistrationBean( new DandelionServlet(), "/dandelion-assets/*" );
	}
}