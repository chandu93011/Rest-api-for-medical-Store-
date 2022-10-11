package com.mphrx.medicine.services;




import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import com.mphrx.medicine.entities.Medicine;
import com.mphrx.medicine.entities.Order;
import com.mphrx.medicine.response.SearchResponce;

 public interface MedicineService {
	public String uploadFile(MultipartFile file) throws Exception;
	public SearchResponce<Medicine> getMedicines(Map<String, Object> medSearchRequest) throws Exception;
	public SearchResponce<Medicine> getMedicine(Map<String, Object> medSearchRequest) throws Exception;
	public String placeOrder(Order order) throws Exception;
	public SearchResponce<Order> getOrder(Map<String, Object> ordSearchRequest) throws Exception;

 }
 

 