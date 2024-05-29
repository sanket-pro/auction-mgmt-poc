package com.auction.bid.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.client.RestTemplate;

import com.auction.bid.constant.BidResult;
import com.auction.bid.dao.entity.BidEntity;
import com.auction.bid.dao.repo.BidRepo;
import com.auction.bid.model.BidDetails;
import com.auction.bid.model.Response;

@DataJpaTest(properties = {"spring.datasource.url=jdbc:h2:mem:mydb",
"spring.jpa.hibernate.ddl-auto=create-drop"})
public class BidServiceJPATest {
	
	@SpyBean
	AuctionService aucService;

	@Autowired
	BidRepo bidRepo;
	
	@SpyBean
	ProductService prodService;
	
	@SpyBean
	UserService userService;
	
	@SpyBean
	BidService bidService;
	
	@SpyBean
	RestTemplate restTemplate;
	
	static Integer buyerId = 2;
	static Integer bidPrice = 100;
	static Integer aid = 1;
	static Integer productId = 2;
	static Integer minPrice = 90;
	
	static BidDetails bidDetails = new BidDetails();
	BidEntity bidEntity;
	
	@BeforeAll
	public static void populateBidObj() {
		
		bidDetails.setAuctionId(aid);
		bidDetails.setBidPrice(bidPrice);
		bidDetails.setProductId(productId);
		bidDetails.setToken(buyerId);
				
	}
	
	@BeforeEach
	public void createBid() {
		bidEntity = new BidEntity();
		
		bidEntity.setBuyerid(buyerId);
		bidEntity.setBidPrice(bidPrice);
		bidEntity.setBiddate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		bidEntity.setAid(aid);
		bidEntity.setPid(productId);
		bidEntity.setMinPrice(minPrice);
		bidEntity.setResult(BidResult.INPROCESS.toString());
		
		bidDetails.setBidId(bidRepo.save(bidEntity).getBidId());
	}
	
	@AfterEach
	public void deleteDateaInProductTable(){
		bidRepo.deleteAll();
		bidRepo.flush();
		bidEntity = null;
	}
	
	@Test
	public void getBidByBuyerIdTest() {
		Response response = bidService.getBidByBuyerId(bidDetails);
		assertEquals("Bids found", response.getMessage());
		assertEquals(bidEntity, ((List<BidEntity>) response.getData()).get(0));
	}
	
	@Test
	public void TestgetBidById() {
		Response response = bidService.getBidById(bidDetails);
		assertEquals("Bid found", response.getMessage());
		assertEquals(bidEntity, response.getData());
	}

}
