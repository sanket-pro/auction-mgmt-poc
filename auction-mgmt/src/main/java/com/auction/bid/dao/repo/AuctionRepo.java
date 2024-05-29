package com.auction.bid.dao.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auction.bid.dao.entity.AuctionEntity;

public interface AuctionRepo extends JpaRepository<AuctionEntity, Integer>{

	Optional<List<AuctionEntity>> findByStatusAndToken(String status, Integer token);
	
	Optional<List<AuctionEntity>> findByStatus(String status);
}
