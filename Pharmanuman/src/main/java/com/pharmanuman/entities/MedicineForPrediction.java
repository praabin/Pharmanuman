package com.pharmanuman.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class MedicineForPrediction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int mfpid;

	@Column(unique = true)
	private LocalDate datum;

	@Column(nullable = false)
	@Min(value = 1, message = "Value must be greater than 0")
	private int mo1ab;

	@Column(nullable = false)
	@Min(value = 1, message = "Value must be greater than 0")
	private int mo1ae;

	@Column(nullable = false)
	@Min(value = 1, message = "Value must be greater than 0")
	private int no2ba;

	@Column(nullable = false)
	@Min(value = 1, message = "Value must be greater than 0")
	private int no2be;

	@Column(nullable = false)
	@Min(value = 1, message = "Value must be greater than 0")
	private int no5b;

	@Column(nullable = false)
	@Min(value = 1, message = "Value must be greater than 0")
	@NotNull(message = "Cannot be null")
	private int no5c;

	@Column(nullable = false)
	@Min(value = 1, message = "Value must be greater than 0")
	private int ro3;

	@Column(nullable = false)
	@Min(value = 1, message = "Value must be greater than 0")
	private int r06;

	@ManyToOne
	private User user;

	public MedicineForPrediction() {
		super();
	}

	public MedicineForPrediction(int mfpid, LocalDate datum,
			@Min(value = 0, message = "Value must be non-negative") int mo1ab,
			@Min(value = 0, message = "Value must be non-negative") int mo1ae,
			@Min(value = 0, message = "Value must be non-negative") int no2ba,
			@Min(value = 0, message = "Value must be non-negative") int no2be,
			@Min(value = 0, message = "Value must be non-negative") int no5b,
			@Min(value = 0, message = "Value must be non-negative") int no5c,
			@Min(value = 0, message = "Value must be non-negative") int ro3,
			@Min(value = 0, message = "Value must be non-negative") int r06, User user) {
		super();
		this.mfpid = mfpid;
		this.datum = datum;
		this.mo1ab = mo1ab;
		this.mo1ae = mo1ae;
		this.no2ba = no2ba;
		this.no2be = no2be;
		this.no5b = no5b;
		this.no5c = no5c;
		this.ro3 = ro3;
		this.r06 = r06;
		this.user = user;
	}

	public int getMfpid() {
		return mfpid;
	}

	public void setMfpid(int mfpid) {
		this.mfpid = mfpid;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public int getMo1ab() {
		return mo1ab;
	}

	public void setMo1ab(int mo1ab) {
		this.mo1ab = mo1ab;
	}

	public int getMo1ae() {
		return mo1ae;
	}

	public void setMo1ae(int mo1ae) {
		this.mo1ae = mo1ae;
	}

	public int getNo2ba() {
		return no2ba;
	}

	public void setNo2ba(int no2ba) {
		this.no2ba = no2ba;
	}

	public int getNo2be() {
		return no2be;
	}

	public void setNo2be(int no2be) {
		this.no2be = no2be;
	}

	public int getNo5b() {
		return no5b;
	}

	public void setNo5b(int no5b) {
		this.no5b = no5b;
	}

	public int getNo5c() {
		return no5c;
	}

	public void setNo5c(int no5c) {
		this.no5c = no5c;
	}

	public int getRo3() {
		return ro3;
	}

	public void setRo3(int ro3) {
		this.ro3 = ro3;
	}

	public int getR06() {
		return r06;
	}

	public void setR06(int r06) {
		this.r06 = r06;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
