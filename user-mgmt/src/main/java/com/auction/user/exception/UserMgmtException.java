package com.auction.user.exception;

public class UserMgmtException extends Exception{

	private static final long serialVersionUID = 1L;
	
	String errorMessage;
	
	public UserMgmtException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
