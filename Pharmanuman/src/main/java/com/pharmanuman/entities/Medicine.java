package com.pharmanuman.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Medicine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int mid;

	
	private String name;
	
	private int quantity;
	private double price;
	private String composition;
	private String type;
	private String description;

	@JsonFormat(pattern = "mm/dd/yyyy", shape = Shape.STRING)
	private String manufacturerDate;

	@JsonFormat(pattern = "mm/dd/yyyy", shape = Shape.STRING)
	private String expiryDate;

	private String dosageForm;
	private String storageInstructions;
	private String manufacturerName;
	private String manufacturerLocation;

	@ManyToOne
	private User user;

	public Medicine() {
		super();
	}

	public Medicine(int mid, String name, int quantity, double price, String composition, String type,
			String description, String manufacturerDate, String expiryDate, String dosageForm,
			String storageInstructions, String manufacturerName, String manufacturerLocation, User user) {
		super();
		this.mid = mid;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.composition = composition;
		this.type = type;
		this.description = description;
		this.manufacturerDate = manufacturerDate;
		this.expiryDate = expiryDate;
		this.dosageForm = dosageForm;
		this.storageInstructions = storageInstructions;
		this.manufacturerName = manufacturerName;
		this.manufacturerLocation = manufacturerLocation;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getComposition() {
		return composition;
	}

	public void setComposition(String composition) {
		this.composition = composition;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public String getManufacturerDate() {
		return manufacturerDate;
	}

	public void setManufacturerDate(String manufacturerDate) {
		this.manufacturerDate = manufacturerDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getDosageForm() {
		return dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}

	public String getStorageInstructions() {
		return storageInstructions;
	}

	public void setStorageInstructions(String storageInstructions) {
		this.storageInstructions = storageInstructions;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getManufacturerLocation() {
		return manufacturerLocation;
	}

	public void setManufacturerLocation(String manufacturerLocation) {
		this.manufacturerLocation = manufacturerLocation;
	}

//	@Override
//	public String toString() {
//		return "Medicine [mid=" + mid + ", name=" + name + ", quantity=" + quantity + ", price=" + price
//				+ ", composition=" + composition + ", type=" + type + ", description=" + description
//				+ ", manufacturerDate=" + manufacturerDate + ", expiryDate=" + expiryDate + ", dosageForm=" + dosageForm
//				+ ", storageInstructions=" + storageInstructions + ", manufacturerName=" + manufacturerName
//				+ ", manufacturerLocation=" + manufacturerLocation + ", user=" + user + "]";
//	}
	
	

}
