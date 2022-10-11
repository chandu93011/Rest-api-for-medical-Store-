package com.mphrx.medicine.controller;



import java.util.Map;

import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import com.mphrx.medicine.entities.Order;
import com.mphrx.medicine.services.MedicineService;


@RestController
public class MedicineController {
	
	private MedicineService medicineService;
	public MedicineController(MedicineService medicineService) {
		this.medicineService=medicineService;
		
	}
	@PostMapping("/uploadCSV")
     public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile  file){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(medicineService.uploadFile(file));
		}catch (IOFileUploadException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		  
    }
	@GetMapping("/medicines")
	 public ResponseEntity<?> getMedicines(@RequestParam Map<String, Object> medSearchRequest) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(medicineService.getMedicines(medSearchRequest)) ;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			
		} 
	
	} 
	@GetMapping("/medicine")
	public ResponseEntity<?> getMedicine(@RequestParam Map<String, Object> medSearchRequest) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(medicineService.getMedicine(medSearchRequest)) ;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}
	@PutMapping("/placeOrder")
	public ResponseEntity<?> placeOrder(@RequestBody Order order){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(medicineService.placeOrder(order));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	@GetMapping("/order")
	 public ResponseEntity<?> getOrder(@RequestParam Map<String, Object> ordSearchRequest) {
			try {
				return ResponseEntity.status(HttpStatus.OK).body(medicineService.getOrder(ordSearchRequest)) ;
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}   
		
	 }
		    
}
	
	

	

		

	
		
	
	


