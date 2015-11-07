package com.engyes.product.repository;

import java.util.List;

import com.engyes.product.model.ProductEntity;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

/**
 * The Interface ProductRepositoryCustom.
 *
 * @author  Bruno Andrade
 */
interface ProductRepositoryCustom {

	/**
	 * Find products with datatables criterias.
	 *
	 * @param criterias the criterias
	 * @return the list
	 */
	List<ProductEntity> findProductsWithDatatablesCriterias( DatatablesCriterias criterias );

	/**
	 * Gets the filtered count.
	 *
	 * @param criterias the criterias
	 * @return the filtered count
	 */
	Long getFilteredCount( DatatablesCriterias criterias );

	/**
	 * Gets the total count.
	 *
	 * @return the total count
	 */
	Long getTotalCount();

}
