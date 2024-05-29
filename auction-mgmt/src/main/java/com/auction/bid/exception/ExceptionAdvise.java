package com.auction.bid.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionAdvise extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler({AuctionException.class})
	public ResponseEntity<Object> handle(AuctionException ex, WebRequest request){
		
		return handleExceptionInternal(ex, ex.getErrorMessage(), 
		          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		
	}
	
	@ExceptionHandler({BidException.class})
	public ResponseEntity<Object> handle(BidException ex, WebRequest request){
		
		return handleExceptionInternal(ex, ex.getErrorMessage(), 
		          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		
	}
	
	@ExceptionHandler({UnknownException.class})
	public ResponseEntity<Object> handle(UnknownException ex, WebRequest request){
		
		return handleExceptionInternal(ex, ex.getErrorMessage(), 
		          new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
		
	}

}
