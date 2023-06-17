package com.pharmanuman.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmanuman.entities.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

}
