package com.engyes.product.service;

import java.util.Collection;

import com.engyes.product.model.Product;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

public interface ProductServiceInterface {

	public Product saveProduct( Product prod );

	public Boolean deleteProduct( Long prodId );

	public Product editProduct( Product prod );

	public Product findProduct( Long prodId );

	public Collection<Product> findProductByName( String name );

	public Collection<Product> findAll();

	public DataSet<Product> findProductsWithDatatablesCriterias( DatatablesCriterias criterias );

}
