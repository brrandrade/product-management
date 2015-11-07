package com.engyes.product.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The Class CategoryEntity.
 *
 * @author  Bruno Andrade
 */
@Entity( name = "Category" )
@Table( name = "category" )
public class CategoryEntity extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The category. */
	@ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.MERGE )
	@JoinColumn( name = "category_id" )
	private CategoryEntity category;

	/** The name. */
	@Column( length = 45 )
	private String name;

	/**
	 * Instantiates a new category entity.
	 */
	public CategoryEntity() {
	}

	/**
	 * Instantiates a new category entity.
	 *
	 * @param name the name
	 */
	public CategoryEntity( String name ) {
		this.name = name;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public CategoryEntity getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory( CategoryEntity category ) {
		this.category = category;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName( String name ) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category [category=" + category + ", name=" + name + "]";
	}

}