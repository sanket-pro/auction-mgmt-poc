package com.auction.bid.model;

public class BidDetails {
	
	Integer bidId;
	
	Integer productId;
	
	Integer bidPrice;
	
	Integer token;
	
	Integer auctionId;

	public Integer getBidId() {
		return bidId;
	}

	public void setBidId(Integer bidId) {
		this.bidId = bidId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(Integer bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Integer getToken() {
		return token;
	}

	public void setToken(Integer token) {
		this.token = token;
	}

	public Integer getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(Integer auctionId) {
		this.auctionId = auctionId;
	}
}
