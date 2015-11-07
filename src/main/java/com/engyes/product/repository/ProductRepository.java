package com.engyes.product.repository;

import org.springframework.data.repository.CrudRepository;

import com.engyes.product.model.ProductEntity;

/**
 * The Interface ProductRepository.
 *
 * @author  Bruno Andrade
 */
public interface ProductRepository extends CrudRepository<ProductEntity, Long>, ProductRepositoryCustom {

}
