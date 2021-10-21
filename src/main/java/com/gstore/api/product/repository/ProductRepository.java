package com.gstore.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gstore.api.product.model.ProductCart;
import com.gstore.api.product.model.ProductList;

@Repository
public interface ProductRepository  extends JpaRepository<ProductList, Integer>{

	void save(ProductCart productcart);


}
