package com.engyes.product.service;

import java.util.Collection;

import com.engyes.product.model.Category;

public interface CategoryServiceInterface {

	public Collection<Category> findAllSorted();

	public Category saveCategory( Category cat );
}
