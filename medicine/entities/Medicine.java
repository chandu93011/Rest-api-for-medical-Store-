package com.mphrx.medicine.entities;

import java.util.Date;

public class Medicine {
	private String name;
	private String batchNo;
	private Date expiryDate;
	private Integer balanceQty;
	private String  packaging;
	private String uniqueCode;
	private String schemes;
	private Float mrp;
	private String manufacturer;
	private Integer hsncode;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Integer getBalanceQty() {
		return balanceQty;
	}
	public void setBalanceQty(Integer balanceQty) {
		this.balanceQty = balanceQty;
	}
	public String getPackaging() {
		return packaging;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public String getUniqueCode() {
		return uniqueCode;
	}
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}
	public String getSchemes() {
		return schemes;
	}
	public void setSchemes(String schemes) {
		this.schemes = schemes;
	}
	public Float getMrp() {
		return mrp;
	}
	public void setMrp(Float mrp) {
		this.mrp = mrp;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Integer getHsncode() {
		return hsncode;
	}
	public void setHsncode(Integer hsncode) {
		this.hsncode = hsncode;
	}
	public Medicine(String name, String batchNo, Date expiryDate, Integer balanceQty, String packaging,
			String uniqueCode, String schemes, Float mrp, String manufacturer, Integer hsncode) {
		super();
		this.name = name;
		this.batchNo = batchNo;
		this.expiryDate = expiryDate;
		this.balanceQty = balanceQty;
		this.packaging = packaging;
		this.uniqueCode = uniqueCode;
		this.schemes = schemes;
		this.mrp = mrp;
		this.manufacturer = manufacturer;
		this.hsncode = hsncode;
	}
	@Override
	public String toString() {
		return "Medicine [name=" + name + ", batchNo=" + batchNo + ", expiryDate=" + expiryDate + ", balanceQty="
				+ balanceQty + ", packaging=" + packaging + ", uniqueCode=" + uniqueCode + ", schemes=" + schemes
				+ ", mrp=" + mrp + ", manufacturer=" + manufacturer + ", hsncode=" + hsncode + "]";
	}	
}