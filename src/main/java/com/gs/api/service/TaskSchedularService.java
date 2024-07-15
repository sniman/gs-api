/* 
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.service;

import com.gs.api.db.entity.RequestProcess;
import com.gs.api.db.repositories.RequestProcessRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskSchedularService {

	@Autowired
    private RequestProcessRepository requestRepository;


	// runs every 5min
    @Scheduled(fixedRate = 350000) 
    public void reportCompletedRequests() {
        List<RequestProcess> completedRequests = requestRepository.findByStatus("COMPLETED");
        completedRequests.forEach(request -> 
            System.out.println("Completed Request: " + request)
        );
    }
}
