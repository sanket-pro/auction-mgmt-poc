package com.auction.bid.exception;

public class BidException extends Exception{
	private static final long serialVersionUID = 1L;
	String errorMessage;
	
	public BidException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
