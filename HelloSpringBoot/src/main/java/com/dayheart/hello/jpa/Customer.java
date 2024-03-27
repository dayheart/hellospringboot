package com.dayheart.hello.jpa;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity(name = "customers")
//@IdClass(Integer.class)
public class Customer implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cust_num", nullable = false)
	private Integer custNum; // prefer Long
	//private int office;
	/*
	 * org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Property 'com.dayheart.hello.jpa.Office.value' belongs to an '@IdClass' but has no matching property in entity class 'com.dayheart.hello.jpa.Office' (every property of the '@IdClass' must have a corresponding persistent property in the '@Entity' class)
	 */
	
	@Column(name = "company", nullable = false)
	private String company;
	
	@Column(name = "cust_rep", nullable = true)
	private Integer custRep;
	
	@Column(name = "credit_limit", nullable = true)
	private Double creditLimit;
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(Integer custNum, String company, Integer custRep, Double creditLimit) {
		super();
		this.custNum = custNum;
		this.company = company;
		this.custRep = custRep;
		this.creditLimit = creditLimit;
	}

	public Integer getCustNum() {
		return custNum;
	}

	public void setCustNum(Integer custNum) {
		this.custNum = custNum;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getCustRep() {
		return custRep;
	}

	public void setCustRep(Integer custRep) {
		this.custRep = custRep;
	}

	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}

	@Override
	public String toString() {
		return "Customer [custNum=" + custNum + ", company=" + company + ", custRep=" + custRep + ", creditLimit="
				+ creditLimit + "]";
	}

	

	
}
