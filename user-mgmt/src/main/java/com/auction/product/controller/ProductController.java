package com.auction.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.product.exception.ProductMgmtException;
import com.auction.product.model.ProductDetails;
import com.auction.product.model.Response;
import com.auction.product.service.ProductService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductService prodService;
	
	@GetMapping("/id")
	public Response getProduct(@PathParam(value = "pid") Integer pid) throws ProductMgmtException {
		
		return prodService.fetchProduct(pid);
	}
	
	@GetMapping
	public Response getProduct() throws ProductMgmtException {
		
		return prodService.fetchAllProduct();
	}
	
	@PostMapping
	public Response addProduct(@RequestBody ProductDetails prodDetails) throws ProductMgmtException{
		
		return  prodService.addProduct(prodDetails);
		
	}

}
