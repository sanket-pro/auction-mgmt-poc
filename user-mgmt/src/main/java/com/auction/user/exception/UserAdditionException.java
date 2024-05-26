package com.auction.user.exception;

public class UserAdditionException extends Exception{

	private static final long serialVersionUID = 1L;
	
	String errorMessage;
	
	public UserAdditionException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
