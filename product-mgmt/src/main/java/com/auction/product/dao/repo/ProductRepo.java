package com.auction.product.dao.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auction.product.dao.entity.ProductEntity;

public interface ProductRepo extends JpaRepository<ProductEntity, Integer>{
 Optional<List<ProductEntity>> findByAvailability(String availability);
 Optional<List<ProductEntity>> findBySellerIdAndAvailability(Integer token, String availability);

}
