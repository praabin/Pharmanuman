package com.pharmanuman.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharmanuman.entities.PlaceOrder;
import com.pharmanuman.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.email = :email")
	public User getUserByUserName(@Param("email") String email);
	
	@Query("select u from User u where u.id = :id")
	public User getUserById(@Param("id") int id);
	
	
	

	List<User> findAll();

	List<User> findByRole(String role);
	

	@Query("FROM User AS u WHERE u.role = :role")
	public List<User> findUserByRole(@Param("role") String role);

}
