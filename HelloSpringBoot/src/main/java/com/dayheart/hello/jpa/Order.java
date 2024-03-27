package com.dayheart.hello.jpa;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

/*
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
*/

//@Entity
//@Table(name = "orders")
@Entity(name = "orders")
public class Order implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7879692942569601545L;

	@Id
	@Column(nullable = false, unique = true, name = "order_num")
	private Long orderNum;
	
	@Column(nullable = false, name = "order_date")
	private Date orderDate;
	
	@Column(nullable = false, name = "cust")
	private int cust; // cust_num
	
	@Column(name = "rep")
	private Integer rep; // empl_num
	
	@Column(nullable = false, name = "MFR")
	private String mfr; // mfr_id
	
	@Column(nullable = false, name = "PRODUCT")
	private String product; // product_id
	
	@Column(nullable = false, name = "QTY")
	private int qty; // 
	
	@Column(nullable = false, name = "AMOUNT")
	private double amount; // money
	
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Order(Long orderNum, Date orderDate, int cust, Integer rep, String mfr, String product, int qty,
			double amount) {
		super();
		this.orderNum = orderNum;
		this.orderDate = orderDate;
		this.cust = cust;
		this.rep = rep;
		this.mfr = mfr;
		this.product = product;
		this.qty = qty;
		this.amount = amount;
	}


	public Long getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}


	public Date getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}


	public int getCust() {
		return cust;
	}


	public void setCust(int cust) {
		this.cust = cust;
	}


	public Integer getRep() {
		return rep;
	}


	public void setRep(Integer rep) {
		this.rep = rep;
	}


	public String getMfr() {
		return mfr;
	}


	public void setMfr(String mfr) {
		this.mfr = mfr;
	}


	public String getProduct() {
		return product;
	}


	public void setProduct(String product) {
		this.product = product;
	}


	public int getQty() {
		return qty;
	}


	public void setQty(int qty) {
		this.qty = qty;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		return "Order [orderNum=" + orderNum + ", orderDate=" + orderDate + ", cust=" + cust + ", rep=" + rep + ", mfr="
				+ mfr + ", product=" + product + ", qty=" + qty + ", amount=" + amount + "]";
	}
	
	
}
