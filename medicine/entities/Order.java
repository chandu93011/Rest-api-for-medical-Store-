package com.mphrx.medicine.entities;

import java.util.Date;

public class Order {
	
	private String id;
	private String uniqueId;
	private Integer quantity;
	private String name;
	private Date orderDate=new Date();
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Order(String id, String uniqueId, Integer quantity, String name) {
		super();
		this.id = id;
		this.uniqueId = uniqueId;
		this.quantity = quantity;
		this.name = name;
	}
	public Order() {
		super();
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", uniqueId=" + uniqueId + ", quantity=" + quantity + ", name=" + name + "]";
	}
	
	
	
	
}
	
	