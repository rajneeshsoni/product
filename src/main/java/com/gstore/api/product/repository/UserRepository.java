package com.gstore.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gstore.api.product.model.Users;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{

	Users findByUsername(String username);

}
