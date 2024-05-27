package com.auction.product.exception;

public class ProductMgmtException extends Exception{
	
	private static final long serialVersionUID = 1L;
	String errorMessage;
	
	public ProductMgmtException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
