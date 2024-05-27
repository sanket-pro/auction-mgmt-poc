package com.auction.user.dao.repo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.auction.user.constant.UserType;
import com.auction.user.controller.UserController;
import com.auction.user.dao.entity.UserEntity;
import com.auction.user.exception.UserAdditionException;
import com.auction.user.model.Response;
import com.auction.user.model.UserDetails;
import com.auction.user.service.UserService;

@DataJpaTest(properties = {"spring.datasource.url=jdbc:h2:mem:mydb",
		"spring.jpa.hibernate.ddl-auto=create-drop"})
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepo;
	
	@SpyBean
	UserService service;
	
	@SpyBean
	UserController controller;
	
	
	private UserDetails initializeSeller() {
		UserDetails seller = new UserDetails();
		seller.setFirstName("SellerFname");
		seller.setLastName("SellerLname");
		seller.setAddress("Seller Address");
		seller.setEmail("Seller1@email.com");
		return seller;
	}
	
	private UserDetails initializeBuyer() {
		UserDetails seller = new UserDetails();
		seller.setFirstName("BuyerFname");
		seller.setLastName("BuyerLname");
		seller.setAddress("Buyer Address");
		seller.setEmail("Buyer1@email.com");
		return seller;
	}
	
	private boolean compareUserDetails(UserDetails input, UserEntity userEntity) {
		if (input.getFirstName().equals(userEntity.getFirstName()) && input.getLastName().equals(userEntity.getLastName())
				&& input.getAddress().equals(userEntity.getAddress()) && input.getEmail().equals(userEntity.getEmail())) {
			return true;
		}
		return false;
	}
	
	@Test
	void registerSellerTest() throws UserAdditionException {
		Response resp = controller.registerSeller(initializeSeller());
		assertTrue(resp.getMessage().equals("SUCCESS"));
	}
	
	@Test
	void registerBuyerTest() throws UserAdditionException {
		Response resp = controller.registerBuyer(initializeBuyer());
		assertTrue(resp.getMessage().equals("SUCCESS"));
	}
	
	@Test
	void testSellerDataInsertion() throws UserAdditionException {
		UserDetails input = initializeSeller();
		Integer token = userRepo.save(service.populateUserData(input, UserType.SELLER)).getToken();
		UserEntity userEntity = userRepo.findById(token).get();
		assertTrue(userEntity.getType().equals(UserType.SELLER.toString()));
		assertTrue(compareUserDetails(input, userEntity));
	}
	
	@Test
	void testBuyerDataInsertion() throws UserAdditionException {
		UserDetails input = initializeBuyer();
		Integer token = userRepo.save(service.populateUserData(input, UserType.BUYER)).getToken();
		UserEntity userEntity = userRepo.findById(token).get();
		assertTrue(userEntity.getType().equals(UserType.BUYER.toString()));
		assertTrue(compareUserDetails(input, userEntity));
	}
}
