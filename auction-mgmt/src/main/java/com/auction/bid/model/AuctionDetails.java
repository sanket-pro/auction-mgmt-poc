package com.auction.bid.model;

public class AuctionDetails {
	String startDateMMDDYYY;
	String endDateMMDDYYYY;
	Integer token;
	
	public String getStartDateMMDDYYY() {
		return startDateMMDDYYY;
	}
	public void setStartDateMMDDYYY(String startDateMMDDYYY) {
		this.startDateMMDDYYY = startDateMMDDYYY;
	}
	public String getEndDateMMDDYYYY() {
		return endDateMMDDYYYY;
	}
	public void setEndDateMMDDYYYY(String endDateMMDDYYYY) {
		this.endDateMMDDYYYY = endDateMMDDYYYY;
	}
	public Integer getToken() {
		return token;
	}
	public void setToken(Integer token) {
		this.token = token;
	}
}
