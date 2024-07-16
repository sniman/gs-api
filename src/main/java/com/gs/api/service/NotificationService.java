/* 
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gs.api.db.entity.RequestProcess;
import com.gs.api.db.repositories.RequestProcessRepository;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;
    
    @Autowired
    private RequestProcessRepository requestRepository;


    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
     * to notify success create policy 
     */
    public void notifyClient() {
    	List <RequestProcess> notifyList = requestRepository.findByStatus("COMPLETED");
    	System.out.println("Start notifying client");
    	//client webhock uri
        String clientEndpoint = "http://client.domain.com/notify"; // 

        for(RequestProcess request : notifyList) {
        	restTemplate.postForEntity(clientEndpoint, request.getMessage(), String.class);
       
        }
       
    }
}
