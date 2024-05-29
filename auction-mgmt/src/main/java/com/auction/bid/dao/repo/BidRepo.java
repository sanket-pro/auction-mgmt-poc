package com.auction.bid.dao.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.auction.bid.dao.entity.BidEntity;

public interface BidRepo extends JpaRepository<BidEntity, Integer> {
	
	Optional<List<BidEntity>> findByBuyerid(Integer id);
	
	Optional<List<BidEntity>> findByAid(Integer aid);
	
	@Query(value = "select top 1 * from bid where pid=?1 order by bidprice desc, biddate asc",
			nativeQuery = true)
	Optional<BidEntity> findMaxPricedEarliestBid(Integer productId);
	
	@Modifying
	@Query(value = "update bid set result=?1 where bidid!=?2 and pid=?3", nativeQuery = true)
	void updateRemainingBidStatus(String resultStatus, Integer winnerBidId, Integer pid);

}
