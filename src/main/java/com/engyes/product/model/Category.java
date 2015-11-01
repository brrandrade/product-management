package com.engyes.product.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Category extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.MERGE )
	@JoinColumn( name = "category_id" )
	private Category category;

	@Column( length = 45 )
	private String name;

	public Category() {
	}

	public Category( String name ) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory( Category category ) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category [category=" + category + ", name=" + name + "]";
	}

}