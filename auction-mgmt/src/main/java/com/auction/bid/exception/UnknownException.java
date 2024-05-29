package com.auction.bid.exception;

public class UnknownException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	String errorMessage;
	public UnknownException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
