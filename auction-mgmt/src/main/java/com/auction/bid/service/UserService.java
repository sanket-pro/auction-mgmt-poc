package com.auction.bid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.auction.bid.model.Response;


@Service
public class UserService {
	
	@Value("${userservice.url}")
	String userServiceUrl;
	
	@Autowired
	RestTemplate restTemplate;
	
	public boolean isValidUser(Integer token, String type) {
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

}
