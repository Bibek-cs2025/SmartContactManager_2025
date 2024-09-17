package com.example.contactmanager.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.contactmanager.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	 Optional<User> findByEmail(String username);
	 
	 @Query("select u from User u where u.email=:username")
	 public User getUserByUserName(@Param("username") String username);
}
