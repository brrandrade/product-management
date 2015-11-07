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

/**
 * Configuration for Dandelion component to dealing with datatable. It will
 * be used as server side process 
 *
 * @author  Bruno Andrade
 */
@Component
@Configuration
public class DandelionConfig {

	/**
	 * Data tables dialect.
	 *
	 * @return the data tables dialect
	 */
	@Bean
	public DataTablesDialect dataTablesDialect() {
		return new DataTablesDialect();
	}

	/**
	 * Dandelion dialect.
	 *
	 * @return the dandelion dialect
	 */
	@Bean
	public DandelionDialect dandelionDialect() {
		return new DandelionDialect();
	}

	/**
	 * Dandelion filter registration bean.
	 *
	 * @param profile the profile
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean dandelionFilterRegistrationBean(
			@Value( "${dandelion.profile.active}" ) String profile) {
		System.setProperty( "dandelion.profile.active", profile );
		return new FilterRegistrationBean( new DandelionFilter() );
	}

	/**
	 * Datatable filter registration bean.
	 *
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean datatableFilterRegistrationBean() {
		return new FilterRegistrationBean( new DatatablesFilter() );
	}

	/**
	 * Dandelion servlet registration bean.
	 *
	 * @return the servlet registration bean
	 */
	@Bean
	public ServletRegistrationBean dandelionServletRegistrationBean() {
		return new ServletRegistrationBean( new DandelionServlet(), "/dandelion-assets/*" );
	}
}