package com.mphrx.medicine.response;

import java.util.ArrayList;
import java.util.List;

public class SearchResponce<T> {
	
	private List<T> list = new ArrayList<>();
	private int count;
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}
