
/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.service;

public interface PriceService {
	   public double calculatePrice(String productType, int age, double healthStatus);
}
