
/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import com.gs.api.db.entity.Policy;
import com.gs.api.db.entity.RequestProcess;
import com.gs.api.db.repositories.PolicyRepository;
import com.gs.api.db.repositories.RequestProcessRepository;
import com.gs.api.exception.ResourceNotFoundException;
import com.gs.api.service.PolicyService;
import com.gs.api.service.PriceService;
import com.gs.api.vo.model.Product;
import com.gs.api.vo.model.ProductPolicy;
import com.gs.api.vo.request.IssuePolicyRequest;
import com.gs.api.vo.request.UpdatePolicyRequest;
import com.gs.api.vo.response.Response;
import com.gs.api.vo.response.ResponseTokenDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
public class PolicyController {

    @Autowired
    private PolicyService policyService;
    
    @Autowired
    private PriceService priceService;
    


    
    /*
     * this is store and foward approach.Request are put in a message/cache and will be pickup by other 
     * process
     */
	@RequestMapping(value = "/policies/issue", method = RequestMethod.POST)
	public CompletableFuture<ProductPolicy> issuePolicy(@RequestBody IssuePolicyRequest issuePolicyRequest)
			throws Exception {
		
	    //String policyHolderName, String productType,String age,String healthStatus
		return policyService.issuePolicy(issuePolicyRequest.getPolicyHolderName(), issuePolicyRequest.getProductType(),issuePolicyRequest.getAge(),issuePolicyRequest.getHealthStatus());
		
	}

	@GetMapping("/policies/enquiry/{policyId}")
    public Policy policyEnquiry(
    		@PathVariable String policyId) {
        
        return policyService.findPolicyById(policyId);
    }
	
	@GetMapping("/policies/quotation")
    public Product pricingEnquiry(
            @RequestParam String productType,
            @RequestParam int age,
            @RequestParam double healthStatus) {
       		
        double price = priceService.calculatePrice(productType, age, healthStatus);
        
        if (price == -1) {

            throw new ResourceNotFoundException("Invalid product type " + productType);
        }
        
        return new Product(productType, price,"Success");
    }
	
	@GetMapping("/policies/active")
    public ResponseEntity<?>  getActivePolicyList() {
	List<Policy> rr=  new ArrayList<>();
       rr= policyService.getAllActivePolicy();
        return ResponseEntity.ok(rr);
    }
	
	@DeleteMapping(value = "/policies/delete/{policyId}")
	public ResponseEntity<?> deletePolicy(@PathVariable String policyId) {
		System.out.println("Deleting :"+ policyId);
		policyService.deletePolicy(policyId);
	    return new ResponseEntity<>("Policy is deleted successfully", HttpStatus.OK);
	}
	
	@PutMapping("/policies/update/{policyId}")
	public ResponseEntity<Object> updatePolicy(@RequestBody UpdatePolicyRequest updatePolicyRequest) {
		policyService.updatePolicy(updatePolicyRequest);
	    return new ResponseEntity<>("User is updated successfully", HttpStatus.OK);
	}
	
}
