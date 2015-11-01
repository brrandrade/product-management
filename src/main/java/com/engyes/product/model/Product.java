package com.engyes.product.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Product extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column( columnDefinition = "BOOLEAN" )
	@NotNull
	private boolean available;

	@ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.MERGE )
	@JoinColumn( name = "category_id" )
	@NotNull
	private Category category;

	@Size( min = 1, max = 100 )
	@Column( nullable = false, length = 100 )
	@NotNull
	private String description;

	@Size( min = 1, max = 45 )
	@Column( nullable = false, length = 45 )
	@NotNull
	private String name;

	@NotNull
	private double price;

	public Product() {
	}

	public Product( String name, String description, double price, boolean available, Category category ) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.available = available;
		this.category = category;
	}

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable( boolean available ) {
		this.available = available;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory( Category category ) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice( double price ) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [available=" + available + ", category=" + category + ", description=" + description
				+ ", name=" + name + ", price=" + price + "]";
	}

}