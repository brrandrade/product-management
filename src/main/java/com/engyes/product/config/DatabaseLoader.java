package com.engyes.product.config;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.engyes.product.model.Category;
import com.engyes.product.model.Product;
import com.engyes.product.repository.CategoryRepository;
import com.engyes.product.repository.ProductRepository;

@Profile( "dev" )
@Service
public class DatabaseLoader {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@Autowired
	public DatabaseLoader( ProductRepository productRepository, CategoryRepository categoryRepository ) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

 
	@PostConstruct
	@Transactional
	private void initDatabase() {
		categoryRepository.deleteAll();
		productRepository.deleteAll();

		Category cat1 = new Category( "Laptop" );
		categoryRepository.save( cat1 );
		Category cat2 = new Category( "Cellphone" );
		categoryRepository.save( cat2 );
		Category cat3 = new Category( "Tablet" );
		categoryRepository.save( cat3 );

		Product prod = new Product( "Macbook Pro 15", "Laptop 15inch", 15.11, true, cat1 );
		productRepository.save( prod );
		prod = new Product( "Macbook Pro 13", "Laptop 13inch", 10.11, false, cat1 );
		productRepository.save( prod );
		prod = new Product( "Iphone 5", "Cellphone from apple 5th", 18.51, true, cat2 );
		productRepository.save( prod );
		prod = new Product( "Iphone 6", "Laptop 6inch", 101.11, true, cat2 );
		productRepository.save( prod );
		prod = new Product( "Iphone 4", "Laptop 4inch", 9.11, true, cat2 );
		productRepository.save( prod );

		for ( int i = 6 ; i < 500 ; i++ ) {
			prod = new Product( "Iphone " + i, "Laptop " + i + "inch", 9.11 + ( 2 * i ), true, cat2 );
			productRepository.save( prod );
		}

	}
}