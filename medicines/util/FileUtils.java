package com.mphrx.medicines.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


import com.mphrx.medicine.entities.Medicine;


public class FileUtils {
 
	public static List<Medicine> parseCsv(InputStream is){
		BufferedReader fileReader = null;
		CSVParser csvParser = null;
		List<Medicine> medicines = new ArrayList<Medicine>();
		try {
			
			fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			csvParser = new CSVParser(fileReader,CSVFormat.DEFAULT
					.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
	        for (CSVRecord csvRecord : csvRecords) {
	        	SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy", Locale.ENGLISH);
	        	Date date = formatter.parse(csvRecord.get("expiryDate"));
	            Medicine med = new Medicine(
	                                          csvRecord.get("name"),
	                                          csvRecord.get("batchNo"),
	                                          date,
	                                          Integer.parseInt(csvRecord.get("balanceQty")), 
	                                          csvRecord.get("packaging"),
                                              csvRecord.get("uniqueCode"),
	                                          csvRecord.get("schemes"), 
	                                          Float.parseFloat(csvRecord.get("mrp")),
	                                          csvRecord.get("manufacturer"), 
	                                          Integer.parseInt(csvRecord.get("hsnCode")));
	            
	            medicines.add(med);
	        }
					
		} catch (Exception e) {
			 System.out.println("Reading CSV Error!");
			e.printStackTrace();
		}finally {
			try {
				fileReader.close();
				csvParser.close();
			} catch (IOException e) {
				System.out.println("Closing fileReader/csvParser Error!");
				e.printStackTrace();
			}
		}
		return medicines;

           
      
		
	}
}
