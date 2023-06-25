package com.pharmanuman.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharmanuman.entities.MedicineForCompany;
import com.pharmanuman.entities.User;

public interface MedicineForCompanyRepository extends JpaRepository<MedicineForCompany, Integer> {
	
	@Query("from MedicineForCompany as m where m.user.id =:uid")
	public List<MedicineForCompany> findMedicinesById(@Param("uid")int uid);
	
	//search
	

	public List<MedicineForCompany> findByNameContainingAndUser(String name, User user);

}
