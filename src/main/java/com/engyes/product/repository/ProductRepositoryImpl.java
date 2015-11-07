package com.engyes.product.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.engyes.product.model.ProductEntity;
import com.engyes.product.util.RepositoryUtils;
import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

/**
 * The Class ProductRepositoryImpl.
 *
 * @author  Bruno Andrade
 */
class ProductRepositoryImpl implements ProductRepositoryCustom {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.engyes.product.repository.ProductRepositoryCustom#findProductsWithDatatablesCriterias(com.github.dandelion.datatables.core.ajax.DatatablesCriterias)
	 */
	@Override
	public List<ProductEntity> findProductsWithDatatablesCriterias( DatatablesCriterias criterias ) {

		StringBuilder queryBuilder = new StringBuilder( "SELECT p FROM Product p" );

		queryBuilder.append( RepositoryUtils.getFilterQuery( criterias ) );

		if ( criterias.hasOneSortedColumn() ) {

			List<String> orderParams = new ArrayList<String>();
			queryBuilder.append( " ORDER BY " );
			for ( ColumnDef columnDef : criterias.getSortedColumnDefs() ) {
				orderParams.add( "p." + columnDef.getName() + " " + columnDef.getSortDirection() );
			}

			Iterator<String> itr2 = orderParams.iterator();
			while ( itr2.hasNext() ) {
				queryBuilder.append( itr2.next() );
				if ( itr2.hasNext() ) {
					queryBuilder.append( " , " );
				}
			}
		}

		TypedQuery<ProductEntity> query = entityManager.createQuery( queryBuilder.toString(),
				ProductEntity.class );

		query.setFirstResult( criterias.getStart() );
		query.setMaxResults( criterias.getLength() );

		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.engyes.product.repository.ProductRepositoryCustom#getFilteredCount(com.github.dandelion.datatables.core.ajax.DatatablesCriterias)
	 */
	@Override
	public Long getFilteredCount( DatatablesCriterias criterias ) {

		StringBuilder queryBuilder = new StringBuilder( "SELECT p FROM Product p" );

		queryBuilder.append( RepositoryUtils.getFilterQuery( criterias ) );

		Query query = entityManager.createQuery( queryBuilder.toString() );
		return Long.parseLong( String.valueOf( query.getResultList().size() ) );
	}

	/* (non-Javadoc)
	 * @see com.engyes.product.repository.ProductRepositoryCustom#getTotalCount()
	 */
	@Override
	public Long getTotalCount() {
		Query query = entityManager.createQuery( "SELECT COUNT(p) FROM Product p" );
		return (Long)query.getSingleResult();
	}

}
