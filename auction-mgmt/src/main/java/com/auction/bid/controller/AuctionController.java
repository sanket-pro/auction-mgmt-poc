package com.auction.bid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auction.bid.exception.AuctionException;
import com.auction.bid.model.AuctionDetails;
import com.auction.bid.model.Response;
import com.auction.bid.service.AuctionService;

@RestController
@RequestMapping("/auction")
public class AuctionController {
	
	@Autowired
	AuctionService aucService;
	
	@PostMapping()
	public Response createAuction(@RequestBody AuctionDetails aucDetails) throws AuctionException {
		
		return aucService.createAuction(aucDetails);
	}
	
	@GetMapping("/open")
	public Response getOpenAuction() {
		
		return aucService.getAllOpenAuction();
	}
	
	@PatchMapping("/close")
	public Response closeAution(@RequestParam("auctionId") Integer auctionId){
		
		return aucService.closeAuction(auctionId);
	}

}
