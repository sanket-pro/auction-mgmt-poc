package com.auction.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.auction.product.exception.ProductMgmtException;
import com.auction.product.model.Response;

@ExtendWith(MockitoExtension.class)
public class ProductServiceRESTTest {
	
	@Value("${userservice.url}")
	String userServiceUrl;
	
	@Mock
	RestTemplate restTemplate;

	@InjectMocks
	ProductService prodService = new ProductService();
	
	
	
	@Test
	public void testSellerPresentScenario() throws ProductMgmtException {
		
		Integer sellerId = 1;
		Response trueResp = new Response("user validation response", true);
		
		Mockito.when(restTemplate.getForEntity(userServiceUrl, Response.class,sellerId,"SELLER"))
		.thenReturn(new ResponseEntity<Response>(trueResp, HttpStatus.OK));
		
		
		assertEquals(true, prodService.isValidUser(sellerId, "SELLER"));
	}
	
	@Test
	public void testSellerAbsentScenario() throws ProductMgmtException {
		
		Integer sellerId = 2;
		Response trueResp = new Response("user validation response", false);
		
		Mockito.when(restTemplate.getForEntity(userServiceUrl, Response.class,sellerId,"SELLER"))
		.thenReturn(new ResponseEntity<Response>(trueResp, HttpStatus.OK));
		
		assertEquals(false, prodService.isValidUser(sellerId, "SELLER"));
	}
	
}
