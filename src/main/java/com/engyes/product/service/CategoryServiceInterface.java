package com.engyes.product.service;

import java.util.Collection;

import com.engyes.product.model.CategoryEntity;

/**
 * The Interface CategoryServiceInterface.
 *
 * @author  Bruno Andrade
 */
public interface CategoryServiceInterface {

	/**
	 * Find all sorted.
	 *
	 * @return the collection
	 */
	Collection<CategoryEntity> findAllSorted();

	/**
	 * Save category.
	 *
	 * @param cat the cat
	 * @return the category entity
	 */
	CategoryEntity saveCategory( CategoryEntity cat );
}
