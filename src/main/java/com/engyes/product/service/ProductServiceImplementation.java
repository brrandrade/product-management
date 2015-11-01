package com.engyes.product.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engyes.product.model.Product;
import com.engyes.product.repository.ProductRepository;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

@Service
@Transactional
public class ProductServiceImplementation implements ProductServiceInterface {

	@Autowired
	protected ProductRepository productRepository;

	@Override
	public Product saveProduct( Product prod ) {
		return productRepository.save( prod );
	}

	@Override
	public Boolean deleteProduct( Long prodId ) {
		Product tprod = productRepository.findOne( prodId );
		if ( tprod != null ) {
			productRepository.delete( tprod );
			return true;
		}
		return false;
	}

	@Override
	public Product editProduct( Product prod ) {
		return productRepository.save( prod );
	}

	@Override
	public Product findProduct( Long prodId ) {
		return productRepository.findOne( prodId );
	}

	@Override
	public Collection<Product> findProductByName( String name ) {
		return productRepository.findByName( name );
	}

	@Override
	public DataSet<Product> findProductsWithDatatablesCriterias( DatatablesCriterias criterias ) {

		List<Product> products = productRepository.findProductsWithDatatablesCriterias( criterias );
		Long count = productRepository.getTotalCount();
		Long countFiltered = productRepository.getFilteredCount( criterias );

		return new DataSet<Product>( products, count, countFiltered );
	}

	@Override
	public Collection<Product> findAll() {
		return (Collection<Product>)productRepository.findAll();
	}

}
