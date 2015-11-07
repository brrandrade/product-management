package com.engyes.product.service;

import java.util.Collection;

import com.engyes.product.model.ProductEntity;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

/**
 * The Interface ProductServiceInterface.
 *
 * @author  Bruno Andrade
 */
public interface ProductServiceInterface {

	/**
	 * Save product.
	 *
	 * @param prod the prod
	 * @return the product entity
	 */
	ProductEntity saveProduct( ProductEntity prod );

	/**
	 * Delete product.
	 *
	 * @param prodId the prod id
	 * @return the boolean
	 */
	Boolean deleteProduct( Long prodId );

	/**
	 * Edits the product.
	 *
	 * @param prod the prod
	 * @return the product entity
	 */
	ProductEntity editProduct( ProductEntity prod );

	/**
	 * Find product.
	 *
	 * @param prodId the prod id
	 * @return the product entity
	 */
	ProductEntity findProduct( Long prodId );

	/**
	 * Find all.
	 *
	 * @return the collection
	 */
	Collection<ProductEntity> findAll();

	/**
	 * Find products with datatables criterias.
	 *
	 * @param criterias the criterias
	 * @return the data set
	 */
	DataSet<ProductEntity> findProductsWithDatatablesCriterias( DatatablesCriterias criterias );

}
