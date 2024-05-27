package com.auction.user.dao.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auction.user.dao.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	Optional<UserEntity> findByTokenAndType(Integer id, String type);
}
