package com.engyes.product.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebMvcSecurity
@Order( Ordered.HIGHEST_PRECEDENCE )
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String[] UNSECURED_RESOURCE_LIST = new String[] { "/resources/**", "/assets/**",
			"/css/**", "/webjars/**", "/images/**", "/dandelion-assets/**", "/dandelion/**",
			"/ddl-debugger/**", "/ajax/**" };

	private static final String[] UNAUTHORIZED_RESOURCE_LIST = new String[] { "/test.html", "/",
			"/unauthorized", "/error*", "/users*" };

	@Override
	public void configure( WebSecurity web ) throws Exception {
		web.ignoring().antMatchers( UNSECURED_RESOURCE_LIST );
	}

	@Override
	protected void configure( HttpSecurity http ) throws Exception {
		// @formatter:off
        http
            .headers()
                .httpStrictTransportSecurity()
                .frameOptions()
                .xssProtection()
            .and()
                .authorizeRequests()
                    .antMatchers(UNAUTHORIZED_RESOURCE_LIST)
                    .permitAll()
            .and()
                .authorizeRequests()
                    .anyRequest()
                    .authenticated()
            .and()
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .permitAll()
            .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/?logout");
        // @formatter:on
	}

	@Autowired
	public void configureGlobal( AuthenticationManagerBuilder auth, @Value( "${access.user}" ) String user,
			@Value( "${access.pwd}" ) String pwd) throws Exception {
		auth.inMemoryAuthentication().withUser( user ).password( pwd ).roles( "USER", "ADMIN" );
	}
}