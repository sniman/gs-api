
/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.service;

import org.springframework.stereotype.Service;


@Service("priceService")
public class PriceServiceImpl implements PriceService{

	@Override
	public double calculatePrice(String productType, int age, double healthStatus) {
		 double basePrice, ageFactor, healthFactor;

	        
	        switch (productType.trim()) {
	            case "term_life":
	                basePrice = 100;
	                ageFactor = 2;
	                healthFactor = 1.5;
	                break;
	            case "whole_life":
	                basePrice = 150;
	                ageFactor = 2.5;
	                healthFactor = 1.8;
	                break;
	            case "auto":
	                basePrice = 50;
	                ageFactor = 1.2;
	                healthFactor = 1.1;
	                break;
	            default:
	                return -1;
	        }
	        
	        
	        return basePrice + (age * ageFactor) + (healthStatus * healthFactor);

	}

}
