package com.engyes.product.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.engyes.product.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	@Query( "SELECT c FROM Category c  order by name asc" )
	public Collection<Category> findAllSorted();
}
