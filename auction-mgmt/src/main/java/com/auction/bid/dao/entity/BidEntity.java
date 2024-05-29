package com.auction.bid.dao.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "BID")
public class BidEntity {
	
	@Column(name="bidid")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Integer bidId;
	
	@Column(name="aid")
	Integer aid;
	
	@Column(name="pid")
	Integer pid;
	
	@Column(name="bidprice")
	Integer bidPrice;
	
	@Column(name="buyerid")
	Integer buyerid;
	
	@Column(name="biddate")
	Timestamp biddate;
	
	@Column(name="result")
	String result;
	
	@Column
	Integer minPrice;

	public Integer getBidId() {
		return bidId;
	}

	public void setBidId(Integer bidId) {
		this.bidId = bidId;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Integer bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Integer getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(Integer buyerid) {
		this.buyerid = buyerid;
	}

	public Timestamp getBiddate() {
		return biddate;
	}

	public void setBiddate(Timestamp biddate) {
		this.biddate = biddate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}
	
}
