package com.engyes.product.config;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.engyes.product.model.CategoryEntity;
import com.engyes.product.model.ProductEntity;
import com.engyes.product.repository.CategoryRepository;
import com.engyes.product.repository.ProductRepository;

/**
 * The Class DatabaseLoader used only for development proposes.
 *
 * @author  Bruno Andrade
 */
@Profile( "dev" )
@Service
public class DatabaseLoader {

	/** The product repository. */
	private final ProductRepository productRepository;

	/** The category repository. */
	private final CategoryRepository categoryRepository;

	/**
	 * Instantiates a new database loader.
	 *
	 * @param productRepository the product repository
	 * @param categoryRepository the category repository
	 */
	@Autowired
	public DatabaseLoader( ProductRepository productRepository, CategoryRepository categoryRepository ) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	/**
	 * Inits the database.
	 */
	@PostConstruct
	@Transactional
	private void initDatabase() {
		categoryRepository.deleteAll();
		productRepository.deleteAll();

		CategoryEntity cat1 = new CategoryEntity( "Laptop" );
		categoryRepository.save( cat1 );
		CategoryEntity cat2 = new CategoryEntity( "Cellphone" );
		categoryRepository.save( cat2 );
		CategoryEntity cat3 = new CategoryEntity( "Tablet" );
		categoryRepository.save( cat3 );

		ProductEntity prod = new ProductEntity( "Macbook Pro 15", "Laptop 15inch", 15.11, true, cat1 );
		productRepository.save( prod );
		prod = new ProductEntity( "Macbook Pro 13", "Laptop 13inch", 10.11, false, cat1 );
		productRepository.save( prod );
		prod = new ProductEntity( "Iphone 5", "Cellphone from apple 5th", 18.51, true, cat2 );
		productRepository.save( prod );
		prod = new ProductEntity( "Iphone 6", "Laptop 6inch", 101.11, true, cat2 );
		productRepository.save( prod );
		prod = new ProductEntity( "Iphone 4", "Laptop 4inch", 9.11, true, cat2 );
		productRepository.save( prod );

		for ( int i = 6 ; i < 500 ; i++ ) {
			prod = new ProductEntity( "Iphone " + i, "Laptop " + i + "inch", 9.11 + ( 2 * i ), true, cat2 );
			productRepository.save( prod );
		}

	}
}