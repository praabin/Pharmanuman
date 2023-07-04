package com.pharmanuman.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharmanuman.entities.MedicineForPrediction;

public interface MedicineForPredictionRepo extends JpaRepository<MedicineForPrediction, Integer> {
	

	@Query("from MedicineForPrediction as m where m.user.id =:uid")
	public List<MedicineForPrediction> findMedicineForPredictionById(@Param("uid")int uid);

	
}
