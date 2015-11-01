package com.engyes.product.repository;

import java.util.List;

import com.engyes.product.model.Product;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

public interface ProductRepositoryCustom {

	public List<Product> findProductsWithDatatablesCriterias( DatatablesCriterias criterias );

	public Long getFilteredCount( DatatablesCriterias criterias );

	public Long getTotalCount();

}
