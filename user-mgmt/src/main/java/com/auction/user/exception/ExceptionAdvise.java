package com.auction.user.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionAdvise extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler({UserMgmtException.class})
	public ResponseEntity<Object> handle(UserMgmtException ex, WebRequest request){
		
		return handleExceptionInternal(ex, ex.getErrorMessage(), 
		          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handle(Exception ex, WebRequest request){
		
		return handleExceptionInternal(ex, "Error occured during User creation, check user data for correctness", 
		          new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
		
	}

}
