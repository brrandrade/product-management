package com.engyes.product.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.engyes.product.model.CategoryEntity;

/**
 * The Interface CategoryRepository.
 *
 * @author  Bruno Andrade
 */
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

	/**
	 * Find all sorted categories.
	 *
	 * @return the collection
	 */
	@Query( "SELECT c FROM Category c  order by name asc" )
	Collection<CategoryEntity> findAllSorted();
}
