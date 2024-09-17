package com.example.contactmanager.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.contactmanager.entity.Contact;
import com.example.contactmanager.entity.User;

public interface ContactRepository extends JpaRepository<Contact,Integer>{
	
	@Query("from Contact as c where c.user.id=:uid")
	public Page<Contact> findUserContact(@Param("uid")int uid,Pageable pageable);
	
	//predefined query
	public List<Contact> findBynameContainingAndUser(String name,User user);
}
