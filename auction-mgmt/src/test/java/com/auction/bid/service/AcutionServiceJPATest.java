package com.auction.bid.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.client.RestTemplate;

import com.auction.bid.constant.AuctionStatus;
import com.auction.bid.dao.entity.AuctionEntity;
import com.auction.bid.dao.repo.AuctionRepo;
import com.auction.bid.dao.repo.BidRepo;

@DataJpaTest(properties = {"spring.datasource.url=jdbc:h2:mem:mydb",
"spring.jpa.hibernate.ddl-auto=create-drop"})
public class AcutionServiceJPATest {
	
	@SpyBean
	AuctionService aucService;
	
	@Autowired
	AuctionRepo aucRepo;
	
	@SpyBean
	BidRepo bidRepo;
	
	@SpyBean
	ProductService prodService;
	
	@SpyBean
	UserService userService;
	
	@SpyBean
	RestTemplate restTemplate;
	
	String product1 = "1:A, 2:B, ";
	Integer sellerToken1 = 1;
	Integer sellerToken2= 2;
	
	@BeforeEach
	public void  createAuction() {
		
		AuctionEntity entity = new AuctionEntity();
		entity.setStartDate(new Date(Calendar.getInstance().getTimeInMillis()));
		entity.setEndDate(new Date(Calendar.getInstance().getTimeInMillis()));
		entity.setProducts(product1);
		entity.setStatus(AuctionStatus.OPEN.toString());
		entity.setToken(sellerToken1);
		aucRepo.save(entity);
		
	}
	
	@AfterEach
	public void deleteDateaInProductTable(){
		aucRepo.deleteAll();
		aucRepo.flush();
	}
	
	@Test
	public void isAuctionOpenforSellerTrueTest() {
		assertTrue(aucService.isAuctionOpenForSeller(sellerToken1));
	}
	
	@Test
	public void isAuctionOpenforSellerFalseTest() {
		assertFalse(aucService.isAuctionOpenForSeller(sellerToken2));
	}

}
