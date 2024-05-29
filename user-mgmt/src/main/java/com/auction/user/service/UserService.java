package com.auction.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auction.user.constant.UserType;
import com.auction.user.dao.entity.UserEntity;
import com.auction.user.dao.repo.UserRepository;
import com.auction.user.exception.UserMgmtException;
import com.auction.user.model.Response;
import com.auction.user.model.UserDetails;

import jakarta.transaction.Transactional;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Transactional(rollbackOn = Exception.class)
	public Response addUser(UserDetails userDetails, UserType utype) throws UserMgmtException{
		try {
			return new Response("SUCCESS", utype.toString()+" registered successfully, please use the token="+
					userRepo.save(populateUserData(userDetails, utype)).getToken() +
					" for future interactions");
		}
		catch (Exception e) {
			throw new UserMgmtException("Error occured during user registration");
		}
	}
	
	public Response validateUser(Integer token, String type) throws UserMgmtException {
		try {
			return new Response("user validation response", userRepo.findByTokenAndType(token, type).isPresent());
		}
		catch (Exception e) {
			throw new UserMgmtException("Error occured during user validation");
		}
	}
	
	public UserEntity populateUserData(UserDetails userDetails, UserType utype) {
		UserEntity user = new UserEntity();
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setEmail(userDetails.getEmail());
		user.setAddress(userDetails.getAddress());
		user.setType(utype.name());
		return user;
	}
}
