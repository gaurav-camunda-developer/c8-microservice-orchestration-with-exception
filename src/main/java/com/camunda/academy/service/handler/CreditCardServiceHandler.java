package com.camunda.academy.service.handler;

import com.camunda.academy.service.CreditCardService;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class CreditCardServiceHandler implements JobHandler{
	
	CreditCardService creditCardService = new CreditCardService();

	@Override
	public void handle(JobClient client, ActivatedJob job) throws Exception {
		creditCardService.chargeCreditCard();
		//client.newCompleteCommand(job.getKey()).send().join();
		
	}
	

}
