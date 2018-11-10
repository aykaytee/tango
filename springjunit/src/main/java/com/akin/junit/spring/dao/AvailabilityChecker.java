package com.akin.junit.spring.dao;

import com.akin.junit.spring.bo.exception.TechnicalFailureException;

public interface AvailabilityChecker {
	String isPostCodeInServicableArea(String postCode) throws TechnicalFailureException;
}
