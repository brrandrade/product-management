package com.engyes.product.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The Class ProductEntity.
 *
 * @author  Bruno Andrade
 */
@Entity(name = "Product")
@Table( name = "product" )
public class ProductEntity extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The available. */
	@Column( columnDefinition = "BOOLEAN" )
	@NotNull
	private boolean available;

	/** The category. */
	@ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.MERGE )
	@JoinColumn( name = "category_id" )
	@NotNull
	private CategoryEntity category;

	/** The description. */
	@Size( min = 1, max = 100 )
	@Column( nullable = false, length = 100 )
	@NotNull
	private String description;

	/** The name. */
	@Size( min = 1, max = 45 )
	@Column( nullable = false, length = 45 )
	@NotNull
	private String name;

	/** The price. */
	@NotNull
	private double price;

	/**
	 * Instantiates a new product entity.
	 */
	public ProductEntity() {
	}

	/**
	 * Instantiates a new product entity.
	 *
	 * @param name the name
	 * @param description the description
	 * @param price the price
	 * @param available the available
	 * @param category the category
	 */
	public ProductEntity( String name, String description, double price, boolean available,
			CategoryEntity category ) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.available = available;
		this.category = category;
	}

	/**
	 * Gets the available.
	 *
	 * @return the available
	 */
	public boolean getAvailable() {
		return available;
	}

	/**
	 * Sets the available.
	 *
	 * @param available the new available
	 */
	public void setAvailable( boolean available ) {
		this.available = available;
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription( String description ) {
		this.description = description;
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

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice( double price ) {
		this.price = price;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [id=" + super.getId() + ", available=" + available + ", category=" + category + ", description=" + description
				+ ", name=" + name + ", price=" + price + "]";
	}

}