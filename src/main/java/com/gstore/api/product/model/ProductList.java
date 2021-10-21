package com.gstore.api.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "productlist")
public class ProductList {
	
	@Id
    @Column(name="product_id")
	private int productId;
	
	@Column(name="product_name")
	private String productName;
	
	@Column(name="productunit_price")
	private Double productunitPrice;
	
	public Double getProductunitPrice() {
		return productunitPrice;
	}
	public void setProductunitPrice(Double productunitPrice) {
		this.productunitPrice = productunitPrice;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	

}
