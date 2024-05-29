package com.auction.bid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.bid.exception.BidException;
import com.auction.bid.model.BidDetails;
import com.auction.bid.model.Response;
import com.auction.bid.service.BidService;

@RestController
@RequestMapping("/bid")
public class BidController {
	
	@Autowired
	BidService bidService;
	
	
	@PostMapping
	public Response createBid(@RequestBody BidDetails bidDetails) throws BidException {
		return bidService.postBid(bidDetails);
	}
	
	@GetMapping("/buyer")
	public Response getBidforUser(@RequestBody BidDetails bidDetails) {
		
		return bidService.getBidByBuyerId(bidDetails);
	}

}
