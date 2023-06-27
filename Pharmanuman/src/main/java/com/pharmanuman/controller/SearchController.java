package com.pharmanuman.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pharmanuman.dao.MedicineForCompanyRepository;
import com.pharmanuman.dao.MedicineRepository;
import com.pharmanuman.dao.UserRepository;
import com.pharmanuman.entities.Medicine;
import com.pharmanuman.entities.MedicineForCompany;
import com.pharmanuman.entities.User;

@RestController
public class SearchController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MedicineRepository medicineRepository;

	@Autowired
	private MedicineForCompanyRepository medicineForCompanyRepository;

	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query, Principal p) {
//		System.out.println(query);
		User tempUser = this.userRepository.getUserByUserName(p.getName());
		List<Medicine> medicines = this.medicineRepository.findByNameContainingAndUser(query, tempUser);
		return ResponseEntity.ok(medicines);
	}
	
	
	

	@GetMapping("/searchstockist/{query}")
	public ResponseEntity<?> searchStockist(@PathVariable("query") String query, Principal p) {
//		System.out.println(query);
		User tempUser = this.userRepository.getUserByUserName(p.getName());
		List<Medicine> medicines = this.medicineRepository.findByNameContainingAndUser(query, tempUser);
		return ResponseEntity.ok(medicines);
	}

	// for pc

	@GetMapping("/searchpc/{query}")
	public ResponseEntity<?> searchPC(@PathVariable("query") String query, Principal p) {
//		System.out.println(query);
		User tempUser = this.userRepository.getUserByUserName(p.getName());
//		List<Medicine> medicines = this.medicineRepository.findByNameContainingAndUser(query, tempUser);
		List<MedicineForCompany> medicines = this.medicineForCompanyRepository
				.findByNameContainingAndUser(query, tempUser);
		return ResponseEntity.ok(medicines);
	}

}
