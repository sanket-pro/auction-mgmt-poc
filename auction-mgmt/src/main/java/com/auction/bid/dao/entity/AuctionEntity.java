package com.auction.bid.dao.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="AUCTION")
public class AuctionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name= "aid")
	public Integer aid;
	
	@Column(name= "startdate")
	public Date startDate;
	
	@Column(name= "enddate")
	public Date endDate;
	
	@JsonIgnore
	@Column(name= "token")
	public Integer token;
	
	@Column(name= "status")
	String status;
	
	@Column(name= "productData")
	String products;
	

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getToken() {
		return token;
	}

	public void setToken(Integer token) {
		this.token = token;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

}