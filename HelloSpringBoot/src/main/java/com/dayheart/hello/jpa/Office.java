package com.dayheart.hello.jpa;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity(name = "offices")
//@IdClass(Integer.class)
public class Office implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "office", nullable = false)
	private Integer office; // prefer Long
	//private int office;
	/*
	 * org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Property 'com.dayheart.hello.jpa.Office.value' belongs to an '@IdClass' but has no matching property in entity class 'com.dayheart.hello.jpa.Office' (every property of the '@IdClass' must have a corresponding persistent property in the '@Entity' class)
	 */
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "region", nullable = false)
	private String region;
	
	@Column(name = "mgr", nullable = true)
	private Integer mgr;
	
	@Column(name = "target", nullable = false)
	private double target;
	
	@Column(name = "sales", nullable = false)
	private double sales;
	
	public Office() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Office(Integer office, String city, String region, Integer mgr, double target, double sales) {
		super();
		this.office = office;
		this.city = city;
		this.region = region;
		this.mgr = mgr;
		this.target = target;
		this.sales = sales;
	}

	public Integer getOffice() {
		return office;
	}

	public void setOffice(Integer office) {
		this.office = office;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Integer getMgr() {
		return mgr;
	}

	public void setMgr(Integer mgr) {
		this.mgr = mgr;
	}

	public double getTarget() {
		return target;
	}

	public void setTarget(double target) {
		this.target = target;
	}

	public double getSales() {
		return sales;
	}

	public void setSales(double sales) {
		this.sales = sales;
	}

	@Override
	public String toString() {
		return "Office [office=" + office + ", city=" + city + ", region=" + region + ", mgr=" + mgr + ", target="
				+ target + ", sales=" + sales + "]";
	}

		
}
