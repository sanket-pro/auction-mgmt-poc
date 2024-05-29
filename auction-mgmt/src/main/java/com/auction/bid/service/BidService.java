package com.auction.bid.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auction.bid.constant.BidResult;
import com.auction.bid.dao.entity.BidEntity;
import com.auction.bid.dao.repo.BidRepo;
import com.auction.bid.exception.BidException;
import com.auction.bid.exception.UnknownException;
import com.auction.bid.model.BidDetails;
import com.auction.bid.model.Response;

@Service
public class BidService {

	@Autowired
	AuctionService aucService;

	@Autowired
	BidRepo bidRepo;
	
	@Autowired
	ProductService prodService;
	
	@Autowired
	UserService userService;

	public Response postBid(BidDetails bidDetails) throws BidException {
		
		//check if auaction is available for bidding
		if (!aucService.isAuctionOpenForBidding(bidDetails)) {
			throw new BidException("No Auction available for Bidding");
		}
		
		//validate if product is valid for auction
		Map<String, Object> product = prodService.getProduct(bidDetails.getProductId());
		if (null == product || null == product.get("pid")) {
			throw new BidException("Invalid Product "+bidDetails.getProductId());
		}
		
		if(bidDetails.getBidPrice() < (Integer)product.get("price")) {
			throw new BidException("Bid disqualified, Bidding price is less than minimum price requirement");
		}
		
		try {
			//validate if buyer is valid
			if(!userService.isValidUser(bidDetails.getToken(), "BUYER")) {
				throw new BidException("Invalid buyer");
			}
		}
		catch (Exception e) {
			throw new BidException("Error Occurred in validating user token");
		}

		return new Response("Bid placed successfully", bidRepo.save(populateBidData(bidDetails, product)));
	}


	private BidEntity populateBidData(BidDetails bid, Map<String, Object> product) {
		BidEntity bidEntity = new BidEntity();
		bidEntity.setBuyerid(bid.getToken());
		bidEntity.setBidPrice(bid.getBidPrice());
		bidEntity.setBiddate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		bidEntity.setAid(bid.getAuctionId());
		bidEntity.setPid((Integer)product.get("pid"));
		bidEntity.setMinPrice((Integer)product.get("price"));
		bidEntity.setResult(BidResult.INPROCESS.toString());

		return bidEntity;
	}

	public Response getBidByBuyerId(BidDetails bidDetails) {
		try {
			Optional<List<BidEntity>> bids = bidRepo.findByBuyerid(bidDetails.getToken());
			if (bids.isPresent() && bids.get().size() > 0) {
				return new Response("Bids found", bids.get());
			} else {
				return new Response("No Bids found", null);
			}
		} catch (Exception e) {
			throw new UnknownException("Error occured while gettig Bid details");
		}

	}

	public Response getBidById(BidDetails bidDetails) {
		try {
			Optional<BidEntity> bid = bidRepo.findById(bidDetails.getBidId());
			if (bid.isPresent()) {
				return new Response("Bid found", bid.get());
			} else {
				return new Response("No Bid found", null);
			}
		} catch (Exception e) {
			throw new UnknownException("Error occured while gettig Bid details");
		}
	}
	
}
