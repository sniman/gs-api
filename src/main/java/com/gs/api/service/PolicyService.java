/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.service;

import com.gs.api.db.entity.Policy;
import com.gs.api.db.entity.RequestProcess;
import com.gs.api.db.repositories.PolicyRepository;
import com.gs.api.db.repositories.RequestProcessRepository;
import com.gs.api.exception.ResourceNotFoundException;
import com.gs.api.vo.model.LivePolicy;
import com.gs.api.vo.model.Product;
import com.gs.api.vo.model.ProductPolicy;
import com.gs.api.vo.request.UpdatePolicyRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service("policyService")
public class PolicyService {
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@Autowired
	private CoreSystemServices coreService;

	@Autowired
	private PriceService priceService;

	@Autowired
	private RequestProcessRepository requestRepository;

	@Autowired
	private PolicyRepository policyRepository;

	@Async
	public CompletableFuture<ProductPolicy> issuePolicy(String policyHolderName, String productType, String age,
			String healthStatus) {
		return CompletableFuture.supplyAsync(() -> {
			double price = 0L;

			// get price
			price = priceService.calculatePrice(productType, Integer.parseInt(age), Double.parseDouble(healthStatus));

			// Generate policy ID
			long timestamp = System.currentTimeMillis();
	        String uuid = UUID.randomUUID().toString();
	        String policyId="POL" + timestamp + "-" + uuid;

			RequestProcess safRequest = new RequestProcess();
			safRequest.setStatus("PENDING");
			safRequest.setMessage("<request><policy><policyId>" + policyId + "</policyId><policyHolderName>"
					+ policyHolderName + "</policyHolderName><price>" + price + "</price><<policyType>" + productType
					+ "</policyType></>policy</request>");
			requestRepository.saveAndFlush(safRequest);

			LivePolicy livePolicy = new LivePolicy();
			livePolicy.setPolicyId(policyId);
			livePolicy.setPolicyHolderAge(age);
			livePolicy.setPolicyHolderName(policyHolderName);
			livePolicy.setHealthFactor(healthStatus);
			livePolicy.setProduct(new Product(productType, price, ""));
			coreService.callExternalSystemSaf(safRequest.getId(), livePolicy);
			return new ProductPolicy(policyId, policyHolderName, productType);
		});
	}

	public Policy findPolicyById(String policyId) {
		Policy existingPolicy = policyRepository.findByPolicyId(policyId);
		if(existingPolicy==null) {
			throw new ResourceNotFoundException("Policy not found with id: " + policyId);
		}

		return existingPolicy;

	}

	public List<Policy> getAllActivePolicy() {

		return policyRepository.findByStatus("ACTV");
	}

	public Policy deletePolicy(String policyId) {
		Policy existingPolicy = policyRepository.findByPolicyId(policyId);
				
		if(existingPolicy==null) {
			throw new ResourceNotFoundException("Policy not found with id: " + policyId);
		}		
		policyRepository.deleteById(existingPolicy.getId());
		return existingPolicy;
	}

	public Policy updatePolicy(UpdatePolicyRequest updateRequest) {
		Policy existingPolicy = policyRepository.findByPolicyId(updateRequest.getPolicyId());
		if(existingPolicy==null) {
			throw new ResourceNotFoundException("Policy not found with id: " + updateRequest.getPolicyId());
		}		
		existingPolicy.setPolicyHolderName(updateRequest.getPolicyHolderName());
		existingPolicy.setProductType(updateRequest.getProductType());
		existingPolicy.setAge(updateRequest.getAge());
		existingPolicy.setHealthFactor(updateRequest.getHealthStatus());
		policyRepository.save(existingPolicy);
		return existingPolicy;
	}

}
