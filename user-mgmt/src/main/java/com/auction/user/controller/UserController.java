package com.auction.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.user.constant.UserType;
import com.auction.user.exception.UserAdditionException;
import com.auction.user.model.Response;
import com.auction.user.model.UserDetails;
import com.auction.user.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/seller")
	public Response registerSeller(@RequestBody UserDetails userDetails) throws UserAdditionException {
		
		return userService.addUser(userDetails, UserType.SELLER);
				
	}
	
	@PostMapping("/buyer")
	public Response registerBuyer(@RequestBody UserDetails userDetails) throws UserAdditionException {
		
		return userService.addUser(userDetails, UserType.BUYER);
	}

}
