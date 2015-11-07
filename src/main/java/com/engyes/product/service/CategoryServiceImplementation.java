package com.engyes.product.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engyes.product.model.CategoryEntity;
import com.engyes.product.repository.CategoryRepository;

/**
 * The Class CategoryServiceImplementation.
 *
 * @author  Bruno Andrade
 */
@Service
@Transactional
class CategoryServiceImplementation implements CategoryServiceInterface {

	/** The category repository. */
	@Autowired
	private CategoryRepository categoryRepository;

	/* (non-Javadoc)
	 * @see com.engyes.product.service.CategoryServiceInterface#findAllSorted()
	 */
	@Override
	public Collection<CategoryEntity> findAllSorted() {
		return  categoryRepository.findAllSorted();
	}

	/* (non-Javadoc)
	 * @see com.engyes.product.service.CategoryServiceInterface#saveCategory(com.engyes.product.model.CategoryEntity)
	 */
	@Override
	public CategoryEntity saveCategory( CategoryEntity cat ) {
		return categoryRepository.save( cat );
	}

}
