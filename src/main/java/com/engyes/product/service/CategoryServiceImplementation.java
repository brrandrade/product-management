package com.engyes.product.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engyes.product.model.Category;
import com.engyes.product.repository.CategoryRepository;

@Service
@Transactional
public class CategoryServiceImplementation implements CategoryServiceInterface {

	@Autowired
	protected CategoryRepository categoryRepository;

	@Override
	public List<Category> findAllSorted() {
		return (List<Category>)categoryRepository.findAllSorted();
	}

	@Override
	public Category saveCategory( Category cat ) {
		return categoryRepository.save( cat );
	}

}
