package com.akin.junit.spring.service;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.akin.junit.spring.bo.exception.TechnicalFailureException;
import com.akin.junit.spring.dao.AvailabilityChecker;
import com.akin.junit.spring.dto.Basket;

@RunWith(SpringJUnit4ClassRunner.class) 								 // spring-test jar
@ContextConfiguration(locations="classpath:application-context.xml")     // spring-test jar
public class BroadbandAddOnServiceImplTest {
	
	private static final String postCode = "W1";
	private static final String postCode2 = "S1";
	private static final String postCode3 = "Z1";
	private static final String serviceStatus = "SERVICE_AVAILABLE";
	private static final String serviceStatus2 = "SERVICE_UNAVAILABLE";
	private static final String serviceStatus3 = "POSTCODE_INVALID";
	
	private static final String productAddOn1 = "BB_FIX_COMMUNICATION_ADD_ON";
	private static final String productAddOn2 = "BB_FIXED_SINGLE_IP_ADDRESS";
	private static List<String> products;
	private static Basket basket;
	private static List<String> products2;
	private static Basket basket2;
	
	
	// inject this dao into the service
	@Mock
	AvailabilityChecker dao;
	
	@Autowired   // wire this service through Spring
	@InjectMocks // inject dao into service
	private BroadbandAddOnServiceImpl service;   
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		products = new ArrayList<String>();
		products.add("TANGO_INTERNET_M");
		basket = new Basket();
		basket.setProducts(products);
		products2 = new ArrayList<String>();
		products2.add("TANGO_INTERNET_S");
		basket2 = new Basket();
		basket2.setProducts(products2);
	}

	@Test
	public void testReturnRelevantAddOns() throws TechnicalFailureException {
		when(dao.isPostCodeInServicableArea(postCode)).thenReturn(serviceStatus);
		// service = new BroadbandAddOnServiceImpl();  // remove as we are using springs Autowired todo DI
		List<String> resultList = service.checkForAddOnProducts(basket, postCode);
		String result1 = resultList.get(0);
		String result2 = resultList.get(1);
		assertEquals(result1, productAddOn1);
		assertEquals(result2, productAddOn2);
		
	}

	@Test
	public void testEmptyPostCodeNoCompatibleProducts() throws TechnicalFailureException {
		List<String> resultList = service.checkForAddOnProducts(basket2, "");
		boolean result1 = resultList.isEmpty();
		assertEquals(result1, true);
	}	

	@Test
	public void testNonAddOnAreaCompatibleProducts() throws TechnicalFailureException {
		when(dao.isPostCodeInServicableArea(postCode2)).thenReturn(serviceStatus2);
		List<String> resultList = service.checkForAddOnProducts(basket, postCode2);
	}	
	
	@Test
	public void testInvalidPostCodes() throws TechnicalFailureException {
		when(dao.isPostCodeInServicableArea(postCode3)).thenReturn(serviceStatus3);
		List<String> resultList = service.checkForAddOnProducts(basket, postCode3);
		boolean result1 = resultList.isEmpty();
		assertEquals(result1, true);
	}

}
