package com.akin.junit.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.akin.junit.spring.bo.exception.TechnicalFailureException;

@Component
public class AvailabilityCheckerImpl implements AvailabilityChecker {
	
	List<String> valid_postcodes;
	List<String> unavailable_postcodes;
	List<String> planned_postcodes;
	public AvailabilityCheckerImpl() {
		
		valid_postcodes = new ArrayList<String>();
		unavailable_postcodes = new ArrayList<String>();
		planned_postcodes = new ArrayList<String>();
		
		valid_postcodes.add("W1");
		valid_postcodes.add("SW1");
		valid_postcodes.add("NW1");
		valid_postcodes.add("E1");
		planned_postcodes.add("SE1");
		planned_postcodes.add("NE1");
		unavailable_postcodes.add("S1");
		unavailable_postcodes.add("N1");
	}

	@Override
	public String isPostCodeInServicableArea(String postCode) throws TechnicalFailureException {
		String result = null;
		if(valid_postcodes.contains(postCode))
			result = "SERVICE_AVAILABLE";
		else if(unavailable_postcodes.contains(postCode)) {
			result = "SERVICE_UNAVAILABLE";
		}
		else if(planned_postcodes.contains(postCode))
			result = "SERVICE_PLANNED";
		else
			result = "POST_CODE_INVALID";
		return result; 
	}

}
