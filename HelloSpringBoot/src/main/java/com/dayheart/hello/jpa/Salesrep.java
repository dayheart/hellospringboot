package com.dayheart.hello.jpa;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity(name = "salesreps")
//@IdClass(Integer.class)
public class Salesrep implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "empl_num", nullable = false)
	private Integer emplNum; // prefer Long
	//private int office;
	/*
	 * org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Property 'com.dayheart.hello.jpa.Office.value' belongs to an '@IdClass' but has no matching property in entity class 'com.dayheart.hello.jpa.Office' (every property of the '@IdClass' must have a corresponding persistent property in the '@Entity' class)
	 */
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "age", nullable = true)
	private int age;
	
	@Column(name = "rep_office", nullable = true)
	private Integer repOffice;
	
	@Column(name = "title", nullable = true)
	private String title;
	
	@Column(name = "hire_date", nullable = false)
	private Date hireDate;
	
	@Column(name = "manager", nullable = true)
	private Integer manager;
	
	@Column(name = "quota", nullable = true)
	private Double quota;
	
	@Column(name = "sales", nullable = false)
	private double sales;
	
	public Salesrep() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Salesrep(Integer emplNum, String name, int age, Integer repOffice, String title, Date hireDate,
			Integer manager, Double quota, double sales) {
		super();
		this.emplNum = emplNum;
		this.name = name;
		this.age = age;
		this.repOffice = repOffice;
		this.title = title;
		this.hireDate = hireDate;
		this.manager = manager;
		this.quota = quota;
		this.sales = sales;
	}

	public Integer getEmplNum() {
		return emplNum;
	}

	public void setEmplNum(Integer emplNum) {
		this.emplNum = emplNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Integer getRepOffice() {
		return repOffice;
	}

	public void setRepOffice(Integer repOffice) {
		this.repOffice = repOffice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Integer getManager() {
		return manager;
	}

	public void setManager(Integer manager) {
		this.manager = manager;
	}

	public Double getQuota() {
		return quota;
	}

	public void setQuota(Double quota) {
		this.quota = quota;
	}

	public double getSales() {
		return sales;
	}

	public void setSales(double sales) {
		this.sales = sales;
	}

	@Override
	public String toString() {
		return "Salesrep [emplNum=" + emplNum + ", name=" + name + ", age=" + age + ", repOffice=" + repOffice
				+ ", title=" + title + ", hireDate=" + hireDate + ", manager=" + manager + ", quota=" + quota
				+ ", sales=" + sales + "]";
	}
	
	
	
}
