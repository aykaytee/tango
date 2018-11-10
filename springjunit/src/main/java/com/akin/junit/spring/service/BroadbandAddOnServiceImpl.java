
package com.akin.junit.spring.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.akin.junit.spring.bo.exception.TechnicalFailureException;
import com.akin.junit.spring.dao.AvailabilityChecker;
import com.akin.junit.spring.dto.Basket;

@Component
public class BroadbandAddOnServiceImpl implements BroadbandAddOnService {
	
	Map<String, List<String>> productsMap;
	public BroadbandAddOnServiceImpl() {
		productsMap = new HashMap<String, List<String>>();
		List<String> Tango_M_LIST = new ArrayList<String>();
		List<String> Tango_L_LIST = new ArrayList<String>();
		Tango_M_LIST.add("BB_FIX_COMMUNICATION_ADD_ON");
		Tango_M_LIST.add("BB_FIXED_SINGLE_IP_ADDRESS");
		Tango_L_LIST.add("BB_FIXED_SINGLE_IP_ADDRESS");
		Tango_L_LIST.add("BB_FIXED_MULTIPLE_IP_ADDRESS");
		
		productsMap.put("TANGO_INTERNET_S", null);
		productsMap.put("TANGO_INTERNET_M", Tango_M_LIST);
		productsMap.put("TANGO_INTERNET_L", Tango_L_LIST);
	}
	
	@Autowired
	private AvailabilityChecker dao;
	
	@Override
	public List<String> checkForAddOnProducts(Basket basket, String postCode){
		// First check for null values and whether the basket contains a single item 'S' to save polling
		List<String> products = basket.getProducts();
		List<String> addOnProducts = new ArrayList<String>();
		String postcodeResponse = null;
			try {
				postcodeResponse = dao.isPostCodeInServicableArea(postCode);
			} catch (TechnicalFailureException e) {
				System.out.println("");
				e.printStackTrace();
			}
			
			System.out.println("postcodeResponse: " + postcodeResponse);
			if(products.size() == 0)
				return addOnProducts;
			if(products.size() == 1 && products.get(0).equals("TANGO_INTERNET_S"))
				return addOnProducts;
			
			if(postcodeResponse.equals("SERVICE_AVAILABLE") && !products.isEmpty()) {
				for(String prod : products) {
					if(productsMap.containsKey(prod))
						addOnProducts.addAll(productsMap.get(prod));
				}
			}
			else if(postcodeResponse.equals("SERVICE_UNAVAILABLE") && !products.isEmpty()) {
				try {
					throw new TechnicalFailureException("Add-Ons are not available in your area");
				} catch (TechnicalFailureException e) {
					// e.printStackTrace();
				}
			}
			else {
				if(postcodeResponse.equals("POSTCODE_INVALID")) {
						System.out.println("The Postcode is invalid");
				}
				return addOnProducts;
			}
		return addOnProducts;
	}

	public AvailabilityChecker getDao() {
		return dao;
	}

	public void setDao(AvailabilityChecker dao) {
		this.dao = dao;
	}
	
}
