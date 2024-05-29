package com.auction.bid.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.auction.bid.constant.Availability;
import com.auction.bid.exception.UnknownException;
import com.auction.bid.model.Response;

@Service
public class ProductService {
	
	@Value("${product.fetch.url}")
	String productServiceUrl;
	
	@Value("${product.update.url}")
	String productServiceUpdateUrl;
	
	@Value("${product.seller.url}")
	String productServiceSellerUrl;
	
	@Autowired
	RestTemplate restTemplate;
	
	public Map<String, Object> getProduct(Integer productId) {
		try {
			Response resp = null;
			ResponseEntity<Response> prodResponse = restTemplate.getForEntity(productServiceUrl,Response.class, productId);
			if (null != prodResponse.getBody()) {
				resp = (Response) prodResponse.getBody();
				if (null != resp.getData()) {
					@SuppressWarnings("unchecked")
					Map<String, Object> product = (Map<String, Object>) resp.getData();
					if (null != product.get("pid") && Availability.YES.toString().equals(product.get("availability"))) {
						return product;
					}
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnknownException("Error occured while fetching product");
		}
	}
	
	public String getProductOfSeller(Integer sellerToken) {
		try {
			Response resp = null;
			ResponseEntity<Response> prodResponse = restTemplate.getForEntity(productServiceSellerUrl,Response.class, sellerToken);
			if (null != prodResponse.getBody()) {
				resp = (Response) prodResponse.getBody();
				if (null != resp.getData()) {
					String product = (String) resp.getData();
					if (null != product && "SUCCESS".equals(resp.getMessage())) {
						return product;
					}
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnknownException("Error occured while fetching products");
		}
	}
}
