package com.pharmanuman.entities;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
public class MedicineForCompany {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int mfcid;

	@Column(nullable = false)
	@NotBlank(message = "Name shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 30 characters required")
	private String name;

	@Column(nullable = false)
	@Min(value = 0, message = "Quantity must be a positive number or zero")
	private int quantity;

	@Column(nullable = false)
	@DecimalMin(value = "0.00", inclusive = true, message = "Price must be a positive number or zero")
	private double price;

	@Column(nullable = false)
	@NotBlank(message = "Composition shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 20 characters required")
	private String composition;

	@Column(nullable = false)
	@NotBlank(message = "Type shouldn't be blank")
	private String type;

	@Column(length = 200, nullable = false)
	@Length(max = 200, message = "Description should be less than 200 character")
	private String description;

	@Column(nullable = false)
	@JsonFormat(pattern = "MM/dd/yyyy", shape = Shape.STRING)
	@Past(message = "Manufacturer date must be in the past")
	private LocalDate manufacturerDate;

	@Column(nullable = false)
	@JsonFormat(pattern = "MM/dd/yyyy", shape = Shape.STRING)
	@Future(message = "Expiry date must be in the future")
	private LocalDate expiryDate;

	@Column(nullable = false)
	@NotBlank(message = "DosageForm shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 20 characters required")
	private String dosageForm;

	@Column(nullable = false)
	@NotBlank(message = "StorageInstructions shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 20 characters required")
	private String storageInstructions;

	@Column(nullable = false)
	@NotBlank(message = "ManufacturerName shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 30 characters required")
	private String manufacturerName;

	@Column(nullable = false)
	@NotBlank(message = "ManufacturerLocation shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 30 characters required")
	private String manufacturerLocation;

	// below fields are for pharmaceuticals company

	@Column(nullable = false)

	@NotBlank(message = "Batch Number shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 30 characters required")
	private String batchNumber;

	@Column(nullable = false)
	@NotBlank(message = "Registration Number shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 30 characters required")
	private String registrationNumber;

	@Column(nullable = false)
	@NotBlank(message = "Country Of Origin shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 30 characters required")
	private String countryOfOrigin;

	@Column(nullable = false)
	@NotBlank(message = "Prescription Required shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 30 characters required")
	private String prescriptionRequired;

	@Column(nullable = false)
	@NotBlank(message = "Packaging shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 30 characters required")
	private String packaging;

	@JsonIgnore
	@ManyToOne
	private User user;

	public MedicineForCompany() {
		super();
	}

	public MedicineForCompany(int mfcid,
			@NotBlank(message = "Name shouldn't be blank") @Size(min = 2, max = 20, message = "min 2 and max 30 characters required") String name,
			@Min(value = 0, message = "Quantity must be a positive number or zero") int quantity,
			@DecimalMin(value = "0.00", inclusive = true, message = "Price must be a positive number or zero") double price,
			@NotBlank(message = "Composition shouldn't be blank") @Size(min = 2, max = 20, message = "min 2 and max 20 characters required") String composition,
			@NotBlank(message = "Type shouldn't be blank") String type,
			@Length(max = 200, message = "Description should be less than 200 character") String description,
			@Past(message = "Manufacturer date must be in the past") LocalDate manufacturerDate,
			@Future(message = "Expiry date must be in the future") LocalDate expiryDate,
			@NotBlank(message = "DosageForm shouldn't be blank") @Size(min = 2, max = 20, message = "min 2 and max 20 characters required") String dosageForm,
			@NotBlank(message = "StorageInstructions shouldn't be blank") @Size(min = 2, max = 20, message = "min 2 and max 20 characters required") String storageInstructions,
			@NotBlank(message = "ManufacturerName shouldn't be blank") @Size(min = 2, max = 20, message = "min 2 and max 30 characters required") String manufacturerName,
			@NotBlank(message = "ManufacturerLocation shouldn't be blank") @Size(min = 2, max = 20, message = "min 2 and max 30 characters required") String manufacturerLocation,
			@NotBlank(message = "Batch Number shouldn't be blank") @Size(min = 2, max = 20, message = "min 2 and max 30 characters required") String batchNumber,
			@NotBlank(message = "Registration Number shouldn't be blank") @Size(min = 2, max = 20, message = "min 2 and max 30 characters required") String registrationNumber,
			@NotBlank(message = "Country Of Origin shouldn't be blank") @Size(min = 2, max = 20, message = "min 2 and max 30 characters required") String countryOfOrigin,
			@NotBlank(message = "Prescription Required shouldn't be blank") @Size(min = 2, max = 20, message = "min 2 and max 30 characters required") String prescriptionRequired,
			@NotBlank(message = "Packaging shouldn't be blank") @Size(min = 2, max = 20, message = "min 2 and max 30 characters required") String packaging,
			User user) {
		super();
		this.mfcid = mfcid;
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
		this.batchNumber = batchNumber;
		this.registrationNumber = registrationNumber;
		this.countryOfOrigin = countryOfOrigin;
		this.prescriptionRequired = prescriptionRequired;
		this.packaging = packaging;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public LocalDate getManufacturerDate() {
		return manufacturerDate;
	}

	public void setManufacturerDate(LocalDate manufacturerDate) {
		this.manufacturerDate = manufacturerDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
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

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public int getMfcid() {
		return mfcid;
	}

	public void setMfcid(int mfcid) {
		this.mfcid = mfcid;
	}

	public String getPrescriptionRequired() {
		return prescriptionRequired;
	}

	public void setPrescriptionRequired(String prescriptionRequired) {
		this.prescriptionRequired = prescriptionRequired;
	}

	/*
	 * @Override public String toString() { return "Medicine [mid=" + mid +
	 * ", name=" + name + ", quantity=" + quantity + ", price=" + price +
	 * ", composition=" + composition + ", type=" + type + ", description=" +
	 * description + ", manufacturerDate=" + manufacturerDate + ", expiryDate=" +
	 * expiryDate + ", dosageForm=" + dosageForm + ", storageInstructions=" +
	 * storageInstructions + ", manufacturerName=" + manufacturerName +
	 * ", manufacturerLocation=" + manufacturerLocation + ", user=" + user + "]"; }
	 * 
	 */
}
