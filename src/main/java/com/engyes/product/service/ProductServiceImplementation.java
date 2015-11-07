package com.engyes.product.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engyes.product.model.ProductEntity;
import com.engyes.product.repository.ProductRepository;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

/**
 * The Class ProductServiceImplementation.
 *
 * @author  Bruno Andrade
 */
@Service
@Transactional
class ProductServiceImplementation implements ProductServiceInterface {

	/** The product repository. */
	@Autowired
	protected ProductRepository productRepository;

	/* (non-Javadoc)
	 * @see com.engyes.product.service.ProductServiceInterface#saveProduct(com.engyes.product.model.ProductEntity)
	 */
	@Override
	public ProductEntity saveProduct( ProductEntity prod ) {
		return productRepository.save( prod );
	}

	/* (non-Javadoc)
	 * @see com.engyes.product.service.ProductServiceInterface#deleteProduct(java.lang.Long)
	 */
	@Override
	public Boolean deleteProduct( Long prodId ) {
		ProductEntity tprod = productRepository.findOne( prodId );
		if ( tprod != null ) {
			productRepository.delete( tprod );
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.engyes.product.service.ProductServiceInterface#editProduct(com.engyes.product.model.ProductEntity)
	 */
	@Override
	public ProductEntity editProduct( ProductEntity prod ) {
		return productRepository.save( prod );
	}

	/* (non-Javadoc)
	 * @see com.engyes.product.service.ProductServiceInterface#findProduct(java.lang.Long)
	 */
	@Override
	public ProductEntity findProduct( Long prodId ) {
		return productRepository.findOne( prodId );
	}

	/* (non-Javadoc)
	 * @see com.engyes.product.service.ProductServiceInterface#findProductsWithDatatablesCriterias(com.github.dandelion.datatables.core.ajax.DatatablesCriterias)
	 */
	@Override
	public DataSet<ProductEntity> findProductsWithDatatablesCriterias( DatatablesCriterias criterias ) {

		List<ProductEntity> products = productRepository.findProductsWithDatatablesCriterias( criterias );
		Long count = productRepository.getTotalCount();
		Long countFiltered = productRepository.getFilteredCount( criterias );

		return new DataSet<ProductEntity>( products, count, countFiltered );
	}

	/* (non-Javadoc)
	 * @see com.engyes.product.service.ProductServiceInterface#findAll()
	 */
	@Override
	public Collection<ProductEntity> findAll() {
		return (Collection<ProductEntity>)productRepository.findAll();
	}

}
