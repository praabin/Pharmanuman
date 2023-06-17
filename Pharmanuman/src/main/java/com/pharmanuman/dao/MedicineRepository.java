package com.pharmanuman.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharmanuman.entities.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
	
	@Query("from Medicine as m where m.user.id =:uid")
	public List<Medicine> findMedicinesById(@Param("uid")int uid);

}
