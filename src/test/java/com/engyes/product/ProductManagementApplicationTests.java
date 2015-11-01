package com.engyes.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.engyes.product.service.ProductServiceInterface;
import com.engyes.product.util.Fixture;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration( classes = ProductManagementApplication.class )
@WebAppConfiguration
@DirtiesContext( classMode = ClassMode.AFTER_EACH_TEST_METHOD )
@IntegrationTest( { "server.port:0" } )
@ActiveProfiles( { "test" } )
public class ProductManagementApplicationTests {

	@Autowired
	private Fixture fixture;

	@Value( "${local.server.port}" )
	public int port;

	@Value( "${access.user}" )
	String user;
	@Value( "${access.pwd}" )
	String pwd;

	@Autowired
	ProductServiceInterface productService;

	private String base;
	private RestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = "http://localhost:" + port;
		template = new TestRestTemplate();
	}

	@Test
	public void testHome() throws Exception {
		ResponseEntity<String> entity = template.getForEntity( base, String.class );
		assertEquals( HttpStatus.OK, entity.getStatusCode() );
		assertTrue( "Wrong body (title doesn't match):\n" + entity.getBody(),
				entity.getBody().contains( "<title>Product Management" ) );
		assertTrue( "Wrong body (did not find Add Product):\n" + entity.getBody(),
				entity.getBody().contains( "Add Product" ) );
	}

	@Test
	public void testDeniedAccess() throws Exception {
		ResponseEntity<String> entity = template.getForEntity( base + "/product/new", String.class );
		assertEquals( HttpStatus.OK, entity.getStatusCode() );
		assertTrue( "Wrong content:\n" + entity.getBody(), entity.getBody().contains( "login" ) );
	}

	@Test
	public void testLoginPage() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept( Arrays.asList( MediaType.TEXT_HTML ) );
		ResponseEntity<String> entity = template.exchange( base + "/login", HttpMethod.GET,
				new HttpEntity<Void>( headers ), String.class );
		assertEquals( HttpStatus.OK, entity.getStatusCode() );
		assertTrue( "Wrong content:\n" + entity.getBody(), entity.getBody().contains( "_csrf" ) );
	}

	@Test
	public void testLoginOk() throws Exception {
		ResponseEntity<String> entity = fixture.executeLogin( user, pwd, base, template );
		assertEquals( HttpStatus.FOUND, entity.getStatusCode() );
		assertTrue( "Wrong location:\n" + entity.getHeaders(),
				entity.getHeaders().getLocation().toString().endsWith( port + "/" ) );
		assertNotNull( "Missing cookie:\n" + entity.getHeaders(), entity.getHeaders().get( "Set-Cookie" ) );
	}

	@Test
	public void testLoginNok() throws Exception {
		ResponseEntity<String> entity = fixture.executeLogin( "kjh3kj4h", "sdfsdf", base, template );
		assertTrue( "Wrong location:\n" + entity.getHeaders(),
				entity.getHeaders().getLocation().toString().endsWith( port + "/login?error" ) );
	}

	@Test
	public void testCreateProd() throws Exception {

		ResponseEntity<String> entity = fixture.executeLogin( user, pwd, base, template );

		assertEquals( productService.findAll().size(), 0 );
		entity = fixture.createProduct( fixture.getJSessionId( entity ), base, template );
		assertEquals( productService.findAll().size(), 1 );
		assertEquals( HttpStatus.OK, entity.getStatusCode() );
		String body = entity.getBody();
		assertNotNull( "Body was null", body );
		assertTrue( "Product created:\n" + body, body.contains( "Product Data Saved Successfully" ) );

	}

	@Test
	public void testEditProd() throws Exception {

		ResponseEntity<String> entity = fixture.executeLogin( user, pwd, base, template );

		String jSessionId = fixture.getJSessionId( entity );
		entity = fixture.createProduct( jSessionId, base, template );
		assertEquals( productService.findAll().size(), 1 );
		entity = fixture.editProduct( jSessionId, base, template );
		assertEquals( productService.findAll().size(), 1 );

		assertEquals( HttpStatus.OK, entity.getStatusCode() );
		String body = entity.getBody();
		assertNotNull( "Body was null", body );
		assertTrue( "Product created:\n" + body, body.contains( "Product Data Edited Successfully" ) );

	}

	@Test
	public void testDeleteProd() throws Exception {

		ResponseEntity<String> entity = fixture.executeLogin( user, pwd, base, template );

		String jSessionId = fixture.getJSessionId( entity );

		entity = fixture.createProduct( jSessionId, base, template );
		assertEquals( productService.findAll().size(), 1 );
		entity = fixture.deleteProduct( jSessionId, base, template );
		assertEquals( productService.findAll().size(), 0 );

		assertEquals( HttpStatus.OK, entity.getStatusCode() );
		String body = entity.getBody();
		assertNotNull( "Body was null", body );
		assertTrue( "Product created:\n" + body, body.contains( "Product Data Deleted Successfully" ) );

	}

	@Test
	public void testCss() throws Exception {
		ResponseEntity<String> entity = template.getForEntity( base + "/assets/css/style.css", String.class );
		assertEquals( HttpStatus.OK, entity.getStatusCode() );
		assertTrue( "Wrong body:\n" + entity.getBody(), entity.getBody().contains( ".panel-heading" ) );
	}
}
