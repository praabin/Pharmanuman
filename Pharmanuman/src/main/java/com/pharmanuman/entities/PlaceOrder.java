package com.pharmanuman.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class PlaceOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int poid;

	private String pharmacyName;

	@Column(nullable = false)
	@NotBlank(message = "Name shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 30 characters required")
	private String name;

	@Column(nullable = false)
	@Min(value = 1, message = "Quantity must be a positive number or one")
	@Max(value = 400, message = "Quantity must be a less than or equals to 400")
//	@NotBlank(message = "can't be blank")
	private int quantity;

	private String status;

	@NotBlank(message = "Location shouldn't be blank")
	@Size(min = 2, max = 20, message = "min 2 and max 30 characters required")
	private String location;

	@NotBlank(message = "Phone number shouldn't be blank")
	@Pattern(regexp = "\\d{10}", message = "Phone number should be a 10-digit number")
	private String phone;

	private double price;

	private double total;

	@NotBlank
	private String stockist;


	@Column(nullable = false)
	@JsonFormat(pattern = "MM/dd/yyyy", shape = Shape.STRING)
//	@NotBlank
	private LocalDate createdDate = LocalDate.now();;

	@Column(nullable = false)
//	@NotBlank(message = "can't be blank")
	@JsonFormat(pattern = "MM/dd/yyyy", shape = Shape.STRING)
	@Future(message = "arriveDate must be in the future")
	private LocalDate arriveDate;

	@ManyToOne
	private User user;

	public PlaceOrder() {
		super();
	}

	public PlaceOrder(int poid, String pharmacyName,
			@NotBlank(message = "Name shouldn't be blank") @Size(min = 2, max = 20, message = "min 2 and max 30 characters required") String name,
			@Min(value = 1, message = "Quantity must be a positive number or one") @Max(value = 400, message = "Quantity must be a less than or equals to 400") @NotBlank(message = "can't be blank") int quantity,
			String status,
			@NotBlank(message = "Location shouldn't be blank") @Size(min = 2, max = 20, message = "min 2 and max 30 characters required") String location,
			@NotBlank(message = "Phone number shouldn't be blank") @Pattern(regexp = "\\d{10}", message = "Phone number should be a 10-digit number") String phone,
			double price, double total, @NotBlank String stockist, LocalDate createdDate,
			@Future(message = "arriveDate must be in the future") LocalDate arriveDate, User user) {
		super();
		this.poid = poid;
		this.pharmacyName = pharmacyName;
		this.name = name;
		this.quantity = quantity;
		this.status = status;
		this.location = location;
		this.phone = phone;
		this.price = price;
		this.total = total;
		this.stockist = stockist;
		this.createdDate = createdDate;
		this.arriveDate = arriveDate;
		this.user = user;
	}

	public int getPoid() {
		return poid;
	}

	public void setPoid(int poid) {
		this.poid = poid;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getStockist() {
		return stockist;
	}

	public void setStockist(String stockist) {
		this.stockist = stockist;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(LocalDate arriveDate) {
		this.arriveDate = arriveDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
