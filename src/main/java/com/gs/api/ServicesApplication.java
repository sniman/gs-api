/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class ServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicesApplication.class, args);
		
		
	}
}
