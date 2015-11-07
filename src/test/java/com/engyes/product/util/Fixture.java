package com.engyes.product.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.engyes.product.model.CategoryEntity;
import com.engyes.product.service.CategoryServiceInterface;

@Component
public class Fixture {

	@Autowired
	CategoryServiceInterface categoryService;

	public HttpHeaders getHeaders( String base, RestTemplate template ) {
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<String> page = template.getForEntity( base + "/login", String.class );
		assertEquals( HttpStatus.OK, page.getStatusCode() );
		String cookie = page.getHeaders().getFirst( "Set-Cookie" );
		headers.set( "Cookie", cookie );
		Matcher matcher = Pattern.compile( "(?s).*name=\"_csrf\".*?value=\"([^\"]+).*" )
				.matcher( page.getBody() );
		assertTrue( "No csrf token: " + page.getBody(), matcher.matches() );
		headers.set( "X-CSRF-TOKEN", matcher.group( 1 ) );
		return headers;
	}

	public ResponseEntity<String> executeLogin( String username, String password, String base,
			RestTemplate template ) throws Exception {
		HttpHeaders headers = getHeaders( base, template );
		headers.setAccept( Arrays.asList( MediaType.TEXT_HTML ) );
		headers.setContentType( MediaType.APPLICATION_FORM_URLENCODED );

		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.set( "username", username );
		form.set( "password", password );

		return template.exchange( base + "/login", HttpMethod.POST,
				new HttpEntity<MultiValueMap<String, String>>( form, headers ), String.class );

	}

	public void insertCategory() {
		CategoryEntity cat = new CategoryEntity();
		cat.setName( "Laptop" );
		categoryService.saveCategory( cat );
	}

	public ResponseEntity<String> createProduct( String jSessionId, String base, RestTemplate template ) {
		String url = base + "/product/new";
		this.insertCategory();

		ResponseEntity<String> entity = proccessCreateOrEdit( jSessionId, template, url, "200" );
		return entity;
	}

	public ResponseEntity<String> editProduct( String jSessionId, String base, RestTemplate template ) {
		String url = base + "/product/1/edit";

		ResponseEntity<String> entity = proccessCreateOrEdit( jSessionId, template, url, "400" );
		return entity;
	}

	public ResponseEntity<String> deleteProduct( String jSessionId, String base, RestTemplate template ) {
		String url = base + "/product/1/delete";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept( Arrays.asList( MediaType.TEXT_HTML ) );
		headers.add( "Cookie", jSessionId );

		ResponseEntity<String> entity = template.exchange( url, HttpMethod.GET,
				new HttpEntity<Void>( headers ), String.class );

		if ( entity.getStatusCode() == HttpStatus.FOUND ) {
			entity = template.exchange( entity.getHeaders().getLocation(), HttpMethod.GET,
					new HttpEntity<Void>( headers ), String.class );
		}
		return entity;
	}

	public ResponseEntity<String> proccessCreateOrEdit( String jSessionId, RestTemplate template, String url,
			String price ) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept( Arrays.asList( MediaType.TEXT_HTML ) );
		headers.add( "Cookie", jSessionId );

		ResponseEntity<String> entity = template.exchange( url, HttpMethod.GET,
				new HttpEntity<Void>( headers ), String.class );

		String body = entity.getBody();
		assertNotNull( "Body was null", body );

		Document doc = Jsoup.parse( body );
		Elements elements = doc.select( "input[name=_csrf]" );

		String csrf = elements.attr( "value" );

		headers.setAccept( Arrays.asList( MediaType.TEXT_HTML ) );
		headers.setContentType( MediaType.APPLICATION_FORM_URLENCODED );
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.set( "name", "Macbook" );
		form.set( "description", "Laptop from apple" );
		form.set( "price", price );
		form.set( "category", "1" );
		form.set( "available", "true" );
		form.set( "_available", "on" );
		form.set( "_csrf", csrf );

		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(
				form, headers );
		entity = template.exchange( url, HttpMethod.POST, httpEntity, String.class );

		if ( entity.getStatusCode() == HttpStatus.FOUND ) {
			entity = template.exchange( entity.getHeaders().getLocation(), HttpMethod.GET,
					new HttpEntity<Void>( headers ), String.class );
		}
		return entity;
	}

	public String getJSessionId( ResponseEntity<String> entity ) {
		String setCookie = entity.getHeaders().get( "Set-Cookie" ).get( 0 );
		String jSessionId = StringUtils.split( setCookie, ";" )[ 0 ];
		return jSessionId;
	}

}