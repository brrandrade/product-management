package com.engyes.product.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.dandelion.core.util.StringUtils;
import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

public class RepositoryUtils {

	private RepositoryUtils() {
	}

	public static StringBuilder getFilterQuery( DatatablesCriterias criterias ) {
		StringBuilder queryBuilder = new StringBuilder();
		List<String> paramList = new ArrayList<String>();

		if ( StringUtils.isNotBlank( criterias.getSearch() ) && criterias.hasOneSearchableColumn() ) {
			queryBuilder.append( " WHERE " );

			for ( ColumnDef columnDef : criterias.getColumnDefs() ) {
				if ( columnDef.isSearchable() && StringUtils.isBlank( columnDef.getSearch() ) ) {
					if ( filterColumns( columnDef ) ) {
						paramList.add( " LOWER(p." + columnDef.getName()
								+ ") LIKE '%?%'".replace( "?", criterias.getSearch().toLowerCase() ) );
					}
				}
			}

			Iterator<String> itr = paramList.iterator();
			while ( itr.hasNext() ) {
				queryBuilder.append( itr.next() );
				if ( itr.hasNext() ) {
					queryBuilder.append( " OR " );
				}
			}
		}

		if ( criterias.hasOneSearchableColumn() && criterias.hasOneFilteredColumn() ) {
			paramList = new ArrayList<String>();

			if ( !queryBuilder.toString().contains( "WHERE" ) ) {
				queryBuilder.append( " WHERE " );
			} else {
				queryBuilder.append( " AND " );
			}

			for ( ColumnDef columnDef : criterias.getColumnDefs() ) {
				if ( columnDef.isSearchable() ) {
					if ( filterColumns( columnDef ) ) {
						if ( StringUtils.isNotBlank( columnDef.getSearchFrom() ) ) {
							paramList.add( "p." + columnDef.getName() + " >= " + columnDef.getSearchFrom() );
						}

						if ( StringUtils.isNotBlank( columnDef.getSearchTo() ) ) {
							paramList.add( "p." + columnDef.getName() + " < " + columnDef.getSearchTo() );
						}

						if ( StringUtils.isNotBlank( columnDef.getSearch() ) ) {
							paramList.add( " LOWER(p." + columnDef.getName()
									+ ") LIKE '%?%'".replace( "?", columnDef.getSearch().toLowerCase() ) );
						}
					}
				}
			}

			Iterator<String> itr = paramList.iterator();
			while ( itr.hasNext() ) {
				queryBuilder.append( itr.next() );
				if ( itr.hasNext() ) {
					queryBuilder.append( " AND " );
				}
			}
		}

		return queryBuilder;
	}

	public static boolean filterColumns( ColumnDef columnDef ) {
		return "name".equalsIgnoreCase( columnDef.getName() )
				|| "description".equalsIgnoreCase( columnDef.getName() )
				|| "category.name".equalsIgnoreCase( columnDef.getName() );
	}
}