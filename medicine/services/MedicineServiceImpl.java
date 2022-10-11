package com.mphrx.medicine.services;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.mphrx.medicine.entities.Medicine;
import com.mphrx.medicine.entities.Order;
import com.mphrx.medicine.response.SearchResponce;
import com.mphrx.medicines.util.FileUtils;


@Service
public  class MedicineServiceImpl implements MedicineService {
	
	@Autowired
	MongoTemplate mongoTemplate; 
	
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	
	@Override
	public String uploadFile(MultipartFile  file) throws Exception  {
		
		 String result ="";
		 if(file.isEmpty()) {
			 throw new Exception("Please choose a file.");
		 }
		 List<Medicine> lstMedicines = new ArrayList<>();
		 List<Medicine> lst =new ArrayList<>();
		try {
			lstMedicines = FileUtils.parseCsv(file.getInputStream());
			lst = (List<Medicine>) mongoTemplate.insertAll(lstMedicines);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOFileUploadException("Error in file parsing or inserting the data into the resource ",new IOException());
		}
		if (lstMedicines.size()==lst.size()) {
			result ="Medicine data uploaded successfully !!";
			
		}else {
			result ="Failed to upload Medicine data.Please try again.";
		}
		 return result;
	}
	
	
	@Override
	public SearchResponce<Medicine> getMedicines(Map<String, Object> medSearchRequest) throws Exception {
		Query query = new Query();
		SearchResponce<Medicine> mResponce=new SearchResponce<Medicine>();
		
		
		if (medSearchRequest.containsKey("name")) {
			query.addCriteria(Criteria.where("name").regex(((String) medSearchRequest.get("name")).toUpperCase()));	
		}
		if (medSearchRequest.containsKey("batchNo")) {
			query.addCriteria(Criteria.where("batchNo").is((String) medSearchRequest.get("batchNo")));	
		}
		Date date = new Date();
		Date endDateOfMedicine=null; 
		if (medSearchRequest.containsKey("getExpiredMedicine")) {
			boolean expiredMedicineCheck=Boolean.parseBoolean((String)(medSearchRequest.get("getExpiredMedicine")));
			if (expiredMedicineCheck==true) {
				query.addCriteria(Criteria.where("expiryDate").lt(date));		
			} else {
				if (medSearchRequest.containsKey("endDateOfMedicine")) {
					try {
						endDateOfMedicine=formatter.parse((String) medSearchRequest.get("endDateOfMedicine"));
					} catch (ParseException e) {
						e.printStackTrace();
						throw new Exception("Error in Date formatting " +e.getMessage());
					}
					
				}
				if (endDateOfMedicine!=null) {
					query.addCriteria(Criteria.where("expiryDate").gte(date).lt(endDateOfMedicine));	
					
				} else {
					query.addCriteria(Criteria.where("expiryDate").gte(date));	

				}
			}
		}else {
			if (medSearchRequest.containsKey("endDateOfMedicine")) {
				try {
					endDateOfMedicine=formatter.parse((String) medSearchRequest.get("endDateOfMedicine"));
				} catch (ParseException e) {
					e.printStackTrace();
					throw new Exception("Error in date formatting" +e.getMessage());
				}	
			}
			if (endDateOfMedicine!=null) {
				query.addCriteria(Criteria.where("expiryDate").gte(date).lt(endDateOfMedicine));	
				
			} else {
				query.addCriteria(Criteria.where("expiryDate").gte(date));	
				}
		}
		if (medSearchRequest.containsKey("uniqueCode")) {
		    query.addCriteria(Criteria.where("uniqueCode").is((String) medSearchRequest.get("uniqueCode")));	
			
		}
		if (medSearchRequest.containsKey("manufacturer")) {
			query.addCriteria(Criteria.where("manufacturer").regex(((String) medSearchRequest.get("manufacturer")).toUpperCase()));	
			
		}
		Document querDoc=query.getQueryObject();
		if (querDoc.get("name")==null && querDoc.get("uniqueCode")==null &&  
				querDoc.get("batchNo")==null && querDoc.get("manufacturer")==null && endDateOfMedicine==null) {
			throw new Exception("Bad Request.Please check your constraints" );
			
		}
		
		List<Medicine> medicines = mongoTemplate.find(query,Medicine.class);
		mResponce.setList(medicines);
		mResponce.setCount(medicines.size());
		return mResponce;
	}
	
	
	@Override
	public SearchResponce<Medicine> getMedicine(Map<String, Object> medSearchRequest) throws Exception {
		SearchResponce<Medicine> mResponce=new SearchResponce<Medicine>();
		if (!medSearchRequest.containsKey("uniqueCode") && !medSearchRequest.containsKey("name") && !medSearchRequest.containsKey("batchNo")) {
			throw new Exception("Bad Request.Please provide uniqueCode,batchNo and name for Medicine data");
		}
		if (StringUtils.isBlank((String)medSearchRequest.get("name")) && StringUtils.isBlank((String)medSearchRequest.get("uniqueCode")) && StringUtils.isBlank((String)medSearchRequest.get("batchNo"))) {
			throw new Exception("Bad Request.Please provide uniqueCode,batchNo and name for Medicine data");
		}
		
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("uniqueCode").is(medSearchRequest.get("uniqueCode")),Criteria.where("name").is(medSearchRequest.get("name")),Criteria.where("batchNo").is(medSearchRequest.get("batchNo")));
		Query query = new Query(criteria);
		Medicine medicine = mongoTemplate.findOne(query, Medicine.class);
		if (medicine!=null) {
			mResponce.setList(Arrays.asList(medicine));
		}else {
			throw new Exception("Bad Request.No medicine data found for the given name,uniqueId and batchNo");
		}
		return mResponce ;
	}
	
	
	@Override
	public String placeOrder(Order order) throws Exception {
		if (order.getUniqueId()==null || order.getName()==null || order.getQuantity()==null) {
			throw new Exception("Bad Request.UniqueId, name or quantity is missing.");
		}
		String result= "";
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("uniqueCode").is(order.getUniqueId()),Criteria.where("name").is(order.getName()),Criteria.where("expiryDate").gte(new Date()),Criteria.where("balanceQty").gt(0));
		Query query = new Query(criteria);
		List<Medicine> med =  mongoTemplate.find(query, Medicine.class);
		if (med.size()<1) {
			return "Medicine out of stock";
		}
		Medicine medicines = med.get(0);
		if(medicines.getBalanceQty()>=order.getQuantity()) {
			medicines.setBalanceQty(medicines.getBalanceQty()-order.getQuantity());
			Document dbDoc  = new Document();
			mongoTemplate.getConverter().write(medicines, dbDoc);
			Update update = Update.fromDocument(dbDoc);
			mongoTemplate.upsert(query, update, Medicine.class);
			mongoTemplate.save(order);
			result="Order placed successfully";
			
		}
		else {
			if (medicines.getBalanceQty()==1) {
				result="Sorry! only "+medicines.getBalanceQty()+" medicine is available in the stock.";
			
			} else {
				result="Only "+medicines.getBalanceQty()+" medicines are available in the stock.Please order accordingly.";
			}
		}
		return result;	
	}
	
	
	@Override
	public SearchResponce<Order> getOrder(Map<String, Object> ordSearchRequest) throws Exception {
		Query query = new Query();
		SearchResponce<Order> orderResponce=new SearchResponce<Order>();
		
		if (ordSearchRequest.containsKey("name")){
			query.addCriteria(Criteria.where("name").regex(((String) ordSearchRequest.get("name")).toUpperCase()));	
		}
		if (ordSearchRequest.containsKey("uniqueId")) {
			query.addCriteria(Criteria.where("uniqueId").is((String) ordSearchRequest.get("uniqueId")));	
		}
		if (ordSearchRequest.containsKey("startDate") || ordSearchRequest.containsKey("endDate")) {
			Date startDate=null;
			Date endDate=null;
			try {
				if (ordSearchRequest.containsKey("startDate")) {
					startDate = formatter.parse((String) ordSearchRequest.get("startDate"));
				}
				if (ordSearchRequest.containsKey("endDate")) {
					endDate = formatter.parse((String) ordSearchRequest.get("endDate"));
				}
				
			}catch (ParseException e) {
				e.printStackTrace();
				throw new Exception("Error in date formatting " +e.getMessage());
			}
			if (startDate!=null && endDate!=null && endDate.compareTo(new Date())<0 &&  startDate.compareTo(endDate)<0) {
				
				query.addCriteria(Criteria.where("orderDate").gte(startDate).lt(endDate)); 
			}else if (startDate!=null && endDate==null && startDate.compareTo(new Date())<0) {
				query.addCriteria(Criteria.where("orderDate").gte(startDate).lt(new Date()));
				
			}
		} 
		if (query.getQueryObject().isEmpty()) {
			throw new Exception("Bad Request.Please request with valid constraints ");
			
		}
		System.out.println(query.getQueryObject().isEmpty());
		List<Order> orders = mongoTemplate.find(query,Order.class);
		orderResponce.setList(orders);
		orderResponce.setCount(orders.size());
		return orderResponce;
		
	}
		
}


	
	
	


