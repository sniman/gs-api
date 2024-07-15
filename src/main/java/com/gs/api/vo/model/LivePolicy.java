package com.gs.api.vo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivePolicy {
	private String policyId;
	private String policyHolderName;
	private String policyHolderAge;
	private String healthFactor;
	private Product product;
}
