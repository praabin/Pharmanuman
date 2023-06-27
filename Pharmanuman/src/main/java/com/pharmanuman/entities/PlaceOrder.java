package com.pharmanuman.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PlaceOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int poid;

	private String name;

	private int quantity;

	private String status;

	private String location;

	private String phone;

	private double price;

	private double total;

	private String stockist;

	@Column(nullable = false)
	@JsonFormat(pattern = "mm/dd/yyyy", shape = Shape.STRING)
	private String createdDate;

	@Column(nullable = false)
	@JsonFormat(pattern = "mm/dd/yyyy", shape = Shape.STRING)
	private String arriveDate;

	@ManyToOne
	private User user;

	public PlaceOrder() {
		super();
	}

	public PlaceOrder(int poid, String name, int quantity, String status, String location, String phone, double price,
			double total, String stockist, String createdDate, String arriveDate, User user) {
		super();
		this.poid = poid;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStockist() {
		return stockist;
	}

	public void setStockist(String stockist) {
		this.stockist = stockist;
	}

}
