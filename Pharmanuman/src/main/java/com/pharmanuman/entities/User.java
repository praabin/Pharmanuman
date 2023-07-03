package com.pharmanuman.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "Name shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 20 characters required")
	private String name;

	@Column(unique = true)
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email")
	private String email;

	private String password;
	private String role;

	@Column(length = 500)
	@Length(max = 500, message = "about should be less than 500 character")
	private String about;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	private List<Medicine> medicines = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	private List<MedicineForCompany> medicinesForCompany = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	private List<PlaceOrder> placeOrders = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	private List<MedicineForPrediction> medicinesForPrediction = new ArrayList<>();

	public User() {
		super();
	}

	public User(int id, String name, String email, String password, String role, boolean enabled, String imageUrl,
			String about) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.about = about;
	}

	public List<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<Medicine> medicines) {
		this.medicines = medicines;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<MedicineForCompany> getMedicinesForCompany() {
		return medicinesForCompany;
	}

	public void setMedicinesForCompany(List<MedicineForCompany> medicinesForCompany) {
		this.medicinesForCompany = medicinesForCompany;
	}

	public List<PlaceOrder> getPlaceOrders() {
		return placeOrders;
	}

	public void setPlaceOrders(List<PlaceOrder> placeOrders) {
		this.placeOrders = placeOrders;
	}

	public List<MedicineForPrediction> getMedicinesForPrediction() {
		return medicinesForPrediction;
	}

	public void setMedicinesForPrediction(List<MedicineForPrediction> medicinesForPrediction) {
		this.medicinesForPrediction = medicinesForPrediction;
	}

}
