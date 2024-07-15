/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuePolicyRequest {
	private static final long serialVersionUID = 5926468583005150707L;
	private String policyHolderName;
	private String productType;
	private String age;
	private String healthStatus;

}
