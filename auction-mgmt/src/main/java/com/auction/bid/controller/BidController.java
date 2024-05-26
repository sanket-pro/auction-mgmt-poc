package com.auction.bid.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auction.bid.model.BidDetails;
import com.auction.bid.model.Response;

@RestController("/bid")
public class BidController {
	
	@PostMapping
	public Response createBid(@RequestBody BidDetails bidDetails) {
		return null;
	}
	
	@GetMapping("/uid")
	public Response getBidforUser(@RequestParam("uid") String uid) {
		
		return null;
	}
}
