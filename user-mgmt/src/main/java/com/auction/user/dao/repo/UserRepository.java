package com.auction.user.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auction.user.dao.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
