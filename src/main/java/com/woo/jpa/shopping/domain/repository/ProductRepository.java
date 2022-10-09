package com.woo.jpa.shopping.domain.repository;

import com.woo.jpa.shopping.domain.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
