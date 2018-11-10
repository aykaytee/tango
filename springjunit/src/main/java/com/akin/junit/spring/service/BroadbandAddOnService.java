package com.akin.junit.spring.service;

import java.util.List;

import com.akin.junit.spring.dto.Basket;

public interface BroadbandAddOnService {
	public List<String> checkForAddOnProducts(Basket basket, String postCode);

}
