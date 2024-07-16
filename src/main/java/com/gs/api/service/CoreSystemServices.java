/* 
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.gs.api.db.entity.Policy;
import com.gs.api.db.entity.RequestProcess;
import com.gs.api.db.repositories.PolicyRepository;
import com.gs.api.db.repositories.RequestProcessRepository;
import com.gs.api.vo.model.LivePolicy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
public class CoreSystemServices {
	public static final Logger logger = LoggerFactory.getLogger(CoreSystemServices.class);
	
    @Autowired
    private RequestProcessRepository requestRepository;
   
    @Autowired
    private PolicyRepository policyRepository;
    
    @Autowired
    NotificationService notificationService;

    @Async
    public CompletableFuture<String> callCoreSystem() {
        try {
          //simulate delay on core system
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture("Response from external system");
    }
    
    
    @Async
    @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallback")
    @Retry(name = "myRetry")
    public CompletableFuture<Void> callExternalSystemSaf(Long policyId,LivePolicy livePolicy) {
    	System.out.println("Updating external system");
        try {
            // Simulating delay in the external system
            Thread.sleep(15000);
            
            RequestProcess request = requestRepository.findById(policyId).orElseThrow(null);
            request.setStatus("COMPLETED");
            requestRepository.save(request);
   
            //insert successfully process  policy to policy table
            Policy policy = new Policy();
            policy.setPolicyHolderName(livePolicy.getPolicyHolderName());
            policy.setActivationDate(new Date());
            policy.setAge(livePolicy.getPolicyHolderAge());
            policy.setHealthFactor(livePolicy.getHealthFactor());
            policy.setPolicyId(livePolicy.getPolicyId());
            policy.setProductType(livePolicy.getProduct().getProductType());
            policy.setStatus("ACTV");
            policyRepository.save(policy);
            notificationService.notifyClient();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    } 
    
    public CompletableFuture<Void> fallback(Long policyId, LivePolicy livePolicy, Throwable throwable) {
        // Handle the fallback logic here
    	logger.info("Fallback triggered");
    	
        return CompletableFuture.completedFuture(null);
    }
    
}
