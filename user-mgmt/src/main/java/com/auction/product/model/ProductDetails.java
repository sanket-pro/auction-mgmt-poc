package com.auction.product.model;


public class ProductDetails {
	public Integer pid;
	public String productName;
	public Integer price;
	public Integer sellerId;
	public String availability;
	
	public ProductDetails(Integer pid, String productName, Integer price, String availability) {
		this.pid = pid;
		this.productName = productName;
		this.price = price;
		this.availability = availability;
	}
	
	public ProductDetails() {
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}
	
}
