package com.engyes.product.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1l;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	protected Long id;

	public Long getId() {
		return id;
	}

	public boolean isNew() {
		return id == null;
	}
}
