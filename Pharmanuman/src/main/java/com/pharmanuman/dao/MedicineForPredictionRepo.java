package com.pharmanuman.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmanuman.entities.MedicineForPrediction;

public interface MedicineForPredictionRepo extends JpaRepository<MedicineForPrediction, Integer> {

	
}
