package com.auction.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.auction.product.constant.Availability;
import com.auction.product.dao.entity.ProductEntity;
import com.auction.product.dao.repo.ProductRepo;
import com.auction.product.exception.ProductMgmtException;
import com.auction.product.model.ProductDetails;
import com.auction.product.model.Response;

@Service
public class ProductService {
	
	@Value("${userservice.url}")
	String userServiceUrl;
	
	@Autowired
	public ProductRepo prodRepo;
	
	@Autowired
	RestTemplate restTemplate;
	
	public Response fetchProduct(Integer pid) throws ProductMgmtException {
		
		try {
			Optional<ProductEntity> product = prodRepo.findById(pid);
			
			if(product.isPresent()) {
				ProductEntity prod = product.get();
				return new Response("Product found!", 
						new ProductDetails(prod.getPid(), 
								prod.getPname(), 
								prod.getPrice(), 
								prod.getAvailability()));
			}
			return new Response("Product not found", null);
		}
		catch (Exception e) {
			throw new ProductMgmtException("Error occured in fetching Product details");
		}
	}
	
	
	
	public Response addProduct(ProductDetails prodDetails) throws ProductMgmtException {
		
		try {
			if(isValidUser(prodDetails.getSellerId(), "SELLER")) {
				return new Response("Product Added with product id", prodRepo.save(populateProduct(prodDetails)).getPid());
			}
			else {
				return new Response("Invalid Seller", null);
			}
		}
		catch (Exception e) {
			throw new ProductMgmtException("Error occured while creating product");
		}
	}
	
	public boolean isValidUser(Integer token, String type) throws ProductMgmtException {
		Response resp;
		ResponseEntity<Response> userResponse = restTemplate.getForEntity(userServiceUrl,Response.class,token,type);
		
		if(null != userResponse.getBody()) {
			resp = (Response) userResponse.getBody();
			if(null != resp.getData() && Boolean.class.isInstance(resp.getData())) {
				return (Boolean)resp.getData();
			}
		}
		return false;
	}
	
	private ProductEntity populateProduct(ProductDetails prodDetails) {
		ProductEntity product = new ProductEntity();	
		product.setPname(prodDetails.getProductName());
		product.setPrice(prodDetails.getPrice());
		product.setSellerId(prodDetails.getSellerId());
		product.setAvailability(Availability.YES.toString());
		
		return product;
	}
	
	public Response updateStatus(Integer pid, String status) {
		Optional<ProductEntity> product =  prodRepo.findById(pid);
		if(product.isPresent()) {
			ProductEntity entity = product.get();
			entity.setAvailability(status);
			prodRepo.save(entity);
			return new Response("SUCCESS", null);
		}
		return null;
	}
	
	public Response getSellerProducts(Integer token) {
		Optional<List<ProductEntity>> products = prodRepo.findBySellerIdAndAvailability(token, Availability.YES.toString());
		if(products.isPresent()) {
			StringBuilder productDetails = new StringBuilder();
			products.get().forEach(product -> {
				productDetails.append(product.getPid()).append(":").append(product.getPname()).append(", ");
			});
			
			return new Response("SUCCESS", productDetails.toString());
		}
		return new Response("No Products found", null);
	}

}
