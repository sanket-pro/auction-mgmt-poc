package com.auction.bid.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auction.bid.constant.AuctionStatus;
import com.auction.bid.constant.BidResult;
import com.auction.bid.dao.entity.AuctionEntity;
import com.auction.bid.dao.entity.BidEntity;
import com.auction.bid.dao.repo.AuctionRepo;
import com.auction.bid.dao.repo.BidRepo;
import com.auction.bid.exception.AuctionException;
import com.auction.bid.exception.BidException;
import com.auction.bid.exception.UnknownException;
import com.auction.bid.model.AuctionDetails;
import com.auction.bid.model.BidDetails;
import com.auction.bid.model.Response;
import com.auction.bid.util.Util;

import jakarta.transaction.Transactional;

@Service
public class AuctionService {

	@Autowired
	AuctionRepo auctionRepo;
	
	@Autowired
	BidRepo bidRepo;
	
	@Autowired
	ProductService prodService;
	
	@Autowired
	UserService userService;

	public Response createAuction(AuctionDetails aucDetails) throws AuctionException {
		// validate if auction dates are not past
		if (Util.isPastDate(aucDetails.getStartDateMMDDYYY()) || 
				Util.isPastDate(aucDetails.getEndDateMMDDYYYY()) ||
				Util.parsetoCalendar(aucDetails.getStartDateMMDDYYY()).after(Util.parsetoCalendar(aucDetails.getEndDateMMDDYYYY()))) {
			throw new AuctionException("Invalid Auction dates");
		}
		//validate if requestor is valid to start Auction
		else if(!userService.isValidUser(aucDetails.getToken(), "SELLER")) {
			throw new AuctionException("Invalid Seller");
		}
		// validate if auction is already present for a seller
		else if (isAuctionOpenForSeller(aucDetails.getToken())) {
			throw new AuctionException("Auction is already present");

		} else {
			return new Response("Auction created successfully!", auctionRepo
					.save(populateAuction(aucDetails)));
		}
	}

	public boolean isAuctionOpenForSeller(Integer token) {
		try {
			return getOpenAuctionForSeller(token).getData() == null ? false : true;
		} catch (Exception e) {
			throw new UnknownException("Error occured while getting Auction details");
		}
	}
	
	public Response getAllOpenAuction() {
		try {
			Optional<List<AuctionEntity>> auction = auctionRepo.findByStatus(AuctionStatus.OPEN.toString());

			if (auction.isPresent() && auction.get().size() > 0) {
				return new Response("Open Auction found!", auction.get());
			} else {
				return new Response("Open Auction not found.", null);
			}
		} catch (Exception e) {
			throw new UnknownException("Error occured while getting Auction details");
		}
	}

	public Response getOpenAuctionForSeller(Integer token) {
		try {
			Optional<List<AuctionEntity>> auction = auctionRepo.findByStatusAndToken(AuctionStatus.OPEN.toString(), token);

			if (auction.isPresent() && auction.get().size() > 0) {
				return new Response("Open Auction found!", auction.get());
			} else {
				return new Response("Open Auction not found.", null);
			}
		} catch (Exception e) {
			throw new UnknownException("Error occured while getting Auction details");
		}
	}

	private AuctionEntity populateAuction(AuctionDetails aucDetails) {
		AuctionEntity aucEntity = new AuctionEntity();
		aucEntity.setStartDate(new Date(Util.parsetoCalendar(aucDetails.getStartDateMMDDYYY()).getTimeInMillis()));

		// calculate End date time to be set at 11:59 PM
		Calendar calEndDate = Util.parsetoCalendar(aucDetails.getEndDateMMDDYYYY());
		calEndDate.set(Calendar.AM_PM, Calendar.PM);
		calEndDate.set(Calendar.HOUR_OF_DAY, 11);
		calEndDate.set(Calendar.MINUTE, 59);

		aucEntity.setEndDate(new Date(calEndDate.getTimeInMillis()));
		aucEntity.setToken(aucDetails.getToken());
		aucEntity.setStatus(AuctionStatus.OPEN.toString());
		
		String products = prodService.getProductOfSeller(aucDetails.getToken());
		if(null != products) {
			aucEntity.setProducts(products);
		}
		else {
			throw new UnknownException("products details not found");
		}

		return aucEntity;
	}
	
	
	@Transactional(rollbackOn = UnknownException.class)
	public Response closeAuction(Integer aucId) {
		try {
			Optional<AuctionEntity> auction = auctionRepo.findById(aucId);
			if(auction.isPresent() && AuctionStatus.OPEN.toString().equals(auction.get().getStatus()) ) {
				AuctionEntity auctionUpdate = auction.get();
				Date today = new Date(Calendar.getInstance().getTimeInMillis());
				if(auctionUpdate.getEndDate().after(today)) {
					return new Response("Auction not closed, end date is future date", auctionUpdate.getEndDate());
				}
				auctionUpdate.setStatus(AuctionStatus.CLOSED.toString());
				auctionRepo.save(auctionUpdate);
				return new Response("Auction evaluation completed, details as follows", 
						evaluateBids(aucId));
			}
			return new Response("No Valid Auction found for evaluation", null);
		}
		catch (Exception e) {
			throw new UnknownException("Error occured while closing Auction");
		}
		
	}
	
	@Transactional(rollbackOn = UnknownException.class)
	public List<BidEntity> evaluateBids(Integer auctionId) {
		try {
			Optional<List<BidEntity>> bidList = bidRepo.findByAid( auctionId);
			Set<Integer> productIdSet = bidList.get().stream().map(BidEntity::getPid).collect(Collectors.toSet());
			List<BidEntity> bidWinnerList = new ArrayList<>();
			
			productIdSet.forEach(productId -> {
				BidEntity winner = bidRepo.findMaxPricedEarliestBid(productId).get();
				winner.setResult(BidResult.WON.toString());
				bidWinnerList.add(winner);
				bidRepo.save(winner);
				bidRepo.updateRemainingBidStatus(BidResult.LOST.toString(), winner.getBidId() , productId);
//				prodService.updateProductAvailability(productId, "NO");
			});
			return bidWinnerList;
		}
		catch (Exception e) {
			throw new UnknownException("Error occured during Bid Evaluation");
		}
	}
	
	public boolean isAuctionOpenForBidding(BidDetails biddetails) throws BidException{
		Optional<AuctionEntity> auction = auctionRepo.findById(biddetails.getAuctionId());
		if(auction.isPresent() && auction.get().getStatus().equals(AuctionStatus.OPEN.toString())) {
			Date today = new Date(Calendar.getInstance().getTimeInMillis());
			if(auction.get().getStartDate().after(today)) {
				return false;
			}
			else {
				//check if requested product is part of the original auction
				if(auction.get().getProducts().contains(biddetails.getProductId()+":")) {
					return true;
				}
				else {
					throw new BidException("Invalid product requested");
				}
				
			}
		}
		return false;
	}
}
