package com.engyes.product.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.engyes.product.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>, ProductRepositoryCustom {

	@Query( "SELECT p FROM Product p   WHERE p.name LIKE %:name%" )
	public Collection<Product> findByName( @Param( "name" ) String name);

}
